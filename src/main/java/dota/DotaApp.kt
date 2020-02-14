package dota

import base.constant.*
import base.utils.DmUtils
import base.utils.Win32Utils
import com.jacob.com.Dispatch
import javafx.scene.image.Image
import sample.base.BaseApp

@AppTitle("Dota传奇助手")
@Resizable(false)
@AppIcon("dota.bmp")
@LayoutId("dota")
class DotaApp : BaseApp(), DmUtils, Win32Utils {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DotaApp::class.java)
        }
    }

    class DotaDM(val dm:Dispatch){
        var isBind = false
        var find_第一关图标 = false
        var find_右箭头 = false
        var find_开始战斗 = false
        var find_关闭对话框 = false
        var hasClick_ZhenZhiDaoLe = false
    }

    private var needStopBattle = false
    var dmList = ArrayList<DotaDM>()

    override fun init(window: Window) {
        window.apply {
            x = 0.0
            y = 0.0
        }

        //初始化大漠
        initDM(1)

        //打开模拟器
        btn("open").click {
//            runApp("E:/ChangZhi/dnplayer2/dnplayer.exe")
        }

        //打开游戏
        btn("startGame").click {
            dmList.forEachIndexed { i, it ->
                //绑定模拟器
                if (!it.isBind) {
                    it.dm.unBindWindow()
                    if (i == 0) {
                        it.dm.bindLDMonitor("雷电模拟器")
                        println("绑定成功")
                        lb("prompt").text = "绑定成功"
                    } else {
                        it.dm.bindLDMonitor("雷电模拟器-$i")
                        println("绑定成功 $i")
                    }
                    enterAndLoginGame(it.dm, i)
                    it.isBind = true
                }
            }

        }

        btn("rebind").click{
            dmList.forEachIndexed { i, it->
                it.dm.unBindWindow()
                if (i == 0) {
                    if(it.dm.bindLDMonitor("雷电模拟器")) alert("绑定成功=雷电模拟器")
                } else {
                    if(it.dm.bindLDMonitor("雷电模拟器-$i")) alert("绑定成功=雷电模拟器-$i")
                }
                it.isBind = true
            }
        }

        btn("battle").click {
            dmList.forEachIndexed {i, it->
                val findZhanYi = it.dm.checkAndClick(it.dm.findPic(608,422,727,453, "战役.bmp", "101010", 0.8, DmUtils.DIR.LR_TB))
//                val findZhanYi = it.dm.checkAndClick(it.dm.findStrFast(608,422,727,453, "战役", "e5cb10-404040", 0.9))
                if (!findZhanYi) {
                    it.dm.capture(608,422,727,453, "test.jpg")
                    iv("iv").image = Image("file:C:\\Users\\Administrator\\damo\\test.jpg")
                    alert("未在游戏初始界面")
                    return@click
                }
                needStopBattle = false
                Thread {
                    Thread.sleep(i*1000L)
                    while (!needStopBattle) {
                        //对于一个流程中必定出现的图片，如果检测到了，就不再在此次流程中继续检测
                        if (!it.find_第一关图标) {
                            if (it.dm.checkAndClick(it.dm.findPic(192,383,257,441, "第一关图标.bmp", "101010", 0.9, DmUtils.DIR.LR_BT))) {
                                it.find_第一关图标 = true
                            }
                        }
                        if (!it.find_右箭头) {
                            if (it.dm.checkAndClick(it.dm.findPic(798,423,879,501, "右箭头.bmp", "101010", 0.9, DmUtils.DIR.RL_BT))) {
                                it.find_右箭头 = true
                            }
                        }
                        if (!it.find_开始战斗) {
                            if (it.dm.checkAndClick(it.dm.findPic(818,449,890,520, "开始战斗.bmp", "101010", 0.9, DmUtils.DIR.RL_BT))) {
                                it.find_开始战斗 = true
                            }
                        }
                        //对于一个流程中可能出现的图片，需要不断进行检测
                        it.dm.checkAndClick(it.dm.findPic(0, 0, 960, 540, "关闭对话框.bmp", "666666", 0.9, DmUtils.DIR.LR_BT))
                        if (it.dm.checkAndClick(it.dm.findPic(0, 0, 960, 540, "继续游戏.bmp", "101010", 0.9, DmUtils.DIR.RL_BT))) {
                            it.find_第一关图标 = false
                            it.find_右箭头 = false
                            it.find_开始战斗 = false
                            it.find_关闭对话框 = false
                        }
                        Thread.sleep(600)
                    }
                }.start()
            }
        }

        btn("stopBattle").click {
            needStopBattle = true
            dmList.forEach {
                it.find_第一关图标 = false
                it.find_右箭头 = false
                it.find_开始战斗 = false
                it.find_关闭对话框 = false
            }
        }


    }

    private fun initDM(dmCount:Int) {
        for(i in 0 until dmCount){
            dmList.add(DotaDM(initDmCom().apply {
                setPath("C:\\Users\\Administrator\\damo")
                setDict(0, "dm_soft.txt")
            }))
        }
    }

    /**
     * 进入并登录游戏
     * @param damo 绑定了特定窗口的大漠对象
     * @param index 大漠对象在集合中的索引值
     */
    private fun enterAndLoginGame(damo:Dispatch, index:Int) {
        Thread {
            Thread.sleep(2000)
            runAndroidAppEx(" sh.lilith.dgame.yiwan.guopan", "sh.lilith.dgame.DGame", when(index){
                0->"emulator-5554"
                else->"emulator-5556"
            })
            while (true) {
                //检查模拟器是否处于横屏模式
                val monitorSize = getWindowSize(null, "雷电模拟器"+if(index==0) "" else "-$index")
                if (monitorSize.first > monitorSize.second) {
                    println("已经进入横屏模式")
                    val result = damo.findStrFast(560,480,690,550, "登录", "ffffff-202020", 0.90)
                    damo.capture(560,480,690,550, "test${index}.jpg")
                    iv("iv$index").image = Image("file:C:\\Users\\Administrator\\damo\\test${index}.jpg")
//                    val result = damo.findPic(560,470,690,550, "dota3.bmp", "101010", 0.9, DmUtils.DIR.RL_BT)

                    if (damo.checkAndClick(result)) {
                        Thread {
                            while (true) {
                                //val result = damo.findPic(700,300,800,350, "进入游戏.bmp", "101010", 0.9)
                                val result = damo.findStrFast(700,270,850,310, "进入游戏", "1e93ff-202020", 0.9)
                                damo.capture(700,300,800,350, "test.jpg")
                                iv("iv").image = Image("file:C:\\Users\\Administrator\\damo\\test.jpg")
                                if (damo.checkAndClick(result)){
                                    Thread {
                                        while (true) {
                                            if (!dmList[index].hasClick_ZhenZhiDaoLe) {
//                                                val result1 = damo.findStrFast(535,540,737, 624, "朕知道了", "ebeac9-202020", 0.9)
                                                val result1 = damo.findPic(535,540,737, 624, "朕知道了.bmp", "101010", 0.9)
                                                if (check(result1)) {
                                                    damo.click(result1)
                                                    dmList[index].hasClick_ZhenZhiDaoLe = true
                                                }
                                            } else {
//                                                val result2 = damo.findStrFast( 553,587,723,642, "进入游戏", "f5c40e-303030", 0.95)
                                                val result2 = damo.findPic(553,587,723,642, "进入游戏.bmp", "101010", 0.9, DmUtils.DIR.LR_BT)
                                                if (damo.checkAndClick(result2)) return@Thread
                                            }
                                            Thread.sleep(500)
                                        }
                                    }.start()
                                    return@Thread
                                }
                                Thread.sleep(1000)
                            }
                        }.start()
                        return@Thread
                    }
                }
                Thread.sleep(1000)
            }
        }.start()

    }


    override fun stop() {
        dmList.forEach {
            it.dm.unBindWindow()
        }
    }

}