package app.dnf.yd

import app.dnf.DnfUtils
import base.constant.AppTitle
import base.constant.Style
import base.constant.Window
import com.jacob.com.Dispatch
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import sample.base.BaseApp
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 黄龙大会脚本
 */
@Style(StageStyle.UTILITY)
@AppTitle("YellowDragonApp")
class YellowDragonApp : BaseApp(), DnfUtils, SkillPresenter {

    //该线程池用于灵活释放技能
    private lateinit var cacheService: ExecutorService
    //该线程池用于执行基本流程
    private lateinit var service: ExecutorService
    //当前的角色
    private var currentCharacter = 1
    //总共角色数量
    private val maxSize = 11
    //大漠组件
    private lateinit var dm: Dispatch
    //是否需要关闭黄龙的任务对话框（针对部分角色）
    private var needCloseTaskDialog = true

    companion object{
        //当此标志为true时，暂停技能循环释放
        var needPauseSkills = false
        //是否处于绑定大漠状态
        var isBind = false
        @JvmStatic
        fun main(args: Array<String>) {
            launch(YellowDragonApp::class.java)
        }
    }

    override fun init(window: Window) {
        initComponent()
        initView(window)
    }

    override fun stop() {
        //当应用停止后必须解除绑定，并关闭线程池
        isBind = false
        dm.unBindWindow().pln("解除绑定结果：")
        service.shutdownNow()
        cacheService.shutdownNow()
    }

    private fun initComponent() {
        dm = initDmCom()
        service = Executors.newFixedThreadPool(10)
        cacheService = Executors.newCachedThreadPool()
        println("注册结果：" + dm.reg())
        println("路径设置结果：" + dm.setPath("${getUserDesktop()}/yellow_dragon"))
//        println("字库设置结果：" + dm.setDict(0,"字库.txt"))
    }

    private fun initView(window: Window) {
        window.isAlwaysOnTop = true
        val lbBind = Label("未绑定游戏")
        val tf = TextField()
        window.scene = Scene(VBox().addChildren(
                lbBind,
                Button("绑定窗口").clickBN {
                    lbBind.text = if (dm.bindDNF()) "绑定成功" else "绑定失败"
                    lbBind.setTextColor("#ff3333")
                    isBind = true
                },
                Button("解除绑定").clickBN {
                    lbBind.text = if (dm.unBindWindow()) "解绑成功" else "解绑失败"
                    lbBind.setTextColor("#33ff33")
                    isBind = false
                },
                Button("开始刷图").clickBN {
                    doScript(dm)
                },
                tf.marginVb(20, 0, 20, 0),
                Button("设置当前角色").clickBN {
                    currentCharacter = tf.text.toInt()
                }
        ).preSize(200, 400).apply {
            alignment = Pos.CENTER
            spacing = 20.0
        })
    }

    private fun doScript(dm: Dispatch) {
        dm keyPress SPACE
        //创建一个线程专门负责“再次挑战”
        service.submit {
            while (isBind) {
                //检查指定位置是否存在“返回城镇.bmp”
                val result = dm.findPic(770, 130, 840, 160, "返回城镇")
                if (check(result)) {
                    //如果存在
                    //暂停技能释放
                    needPauseSkills = true
                    //关闭黄龙任务弹窗
                    if (needCloseTaskDialog) {
                        dm keyPress SPACE
                        s(100)
                        dm keyPress SPACE
                    }
                    s(2000.r(100)) //这个时间不能短于一轮技能的时间总和
                    //自动拾取
                    dm.autoPick()
                    s(100)
                    for (i in 1..19) {
                        dm keyPress X
                        s(200.r())
                    }
                    //查找是否存在置灰的“再次挑战.bmp"
                    if (check(dm.findPic(770, 75, 840, 100, "再次挑战",
                                    "101010", 0.8))) {
                        //返回城镇
                        dm keyPress F12
                        s(3000)
                        //前往选择角色界面
                        dm.goToCharacterPage()
                        s(3000)
                        if (++currentCharacter > maxSize) {
                            //如果角色已经刷满了
                            alert("结束啦！")
                            //退出游戏，并关机
                            dm.exitOs(1)
                        } else {
                            //如果角色没有刷满
                            dm.selectCharacter(currentCharacter)
                            s(3000)
                            goToYellowDragon(false)
                            s(2000)
                            dm keyPress SPACE
                            needPauseSkills = false
                        }
                    } else {
                        //再次挑战
                        dm keyPress B
                        s(6000)
                        needPauseSkills = false
                    }
                }
                s(2000)
            }
        }
        //创建一个线程专门负责跳过对战表
        service.submit {
            while (isBind) {
                //查找指定位置是否有“对.bmp”
                if (check(dm.findPic(420, 60, 470, 100, "对"))) {
                    //如果存在则按下SPACE跳过对战表
                    dm keyPress SPACE
                    //设置不需要暂停技能释放
                    needPauseSkills = false
                    //创建一个线程专门负责循环放技能
                    service.submit {
                        when (currentCharacter) {
                            in 1..9 -> dm.common()
                            10 -> dm.hongYan()
                            11 -> dm.jianHunEx(cacheService)
                        }
                    }
                }
                s(2000)
            }
        }
        //创建一个线程用于监测是否存在卡屏
        service.submit {
            while (isBind){
                s(10000)
                if(dm.isDisplayDead(0,0,100,100,10)){
                    dm.beep(1000, 3000)
                }
            }
        }

    }

    private fun goToYellowDragon(haveClothes: Boolean = true) {
        dm.apply {
            walkDown(1800)
            walkLeft(if (!haveClothes) 2400 else 1800)
            walkUp(1500)
            walkRight(3500)
            walkDown(500)
            walkRight(2000)
        }
    }

}
