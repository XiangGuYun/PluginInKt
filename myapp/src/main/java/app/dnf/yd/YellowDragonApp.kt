package app.dnf.yd

import app.dnf.DnfUtils
import base.constant.*
import com.jacob.com.Dispatch
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import sample.base.BaseApp
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.system.exitProcess

/**
 * 黄龙大会脚本
 */
@AppIcon("java.png")
@Resizable(false)
@Style(StageStyle.UNIFIED)
@AppTitle("YellowDragonApp")
class YellowDragonApp : BaseApp(), DnfUtils, SkillPresenter {
    //DNF句柄输入框
    private lateinit var tfHwnd: TextField
    //该线程池用于灵活释放技能
    private lateinit var cacheService: ExecutorService
    //该线程池用于执行基本流程
    private lateinit var service: ExecutorService
    //当前的角色
    private var currentCharacter = AtomicInteger(1)
    //总共角色数量
    private var maxSize = 12
    //大漠组件，这里需要添加volatile以避免指令重排序
    @Volatile
    private lateinit var dm: Dispatch
    //是否需要关闭黄龙的任务对话框（针对部分角色）
    private var needCloseTaskDialog = true

    companion object {
        //当此标志为true时，暂停技能循环释放
        var needPauseSkills = false
        //是否处于绑定大漠状态
        var isBind = AtomicBoolean(false)

        @JvmStatic
        fun main(args: Array<String>) {
            launch(YellowDragonApp::class.java)
        }
    }

    override fun init(window: Window) {
        initComponent()
        initView(window)
    }

    private fun initComponent() {
        dm = initDmCom()
        dm.enableMouseSync(true, 2000)
        dm.enableKeypadSync(true, 2000)
        dm.enableRealKeypad(true)
//        dm.enableRealMouse(2, 10, 300)
        service = Executors.newFixedThreadPool(4)
        cacheService = Executors.newCachedThreadPool()
        println("注册结果：" + dm.reg())
        println("路径设置结果：" + dm.setPath("${getUserDesktop()}/yellow_dragon"))
//        println("字库设置结果：" + dm.setDict(0, "字库.txt"))
    }

    private fun initView(window: Window) {
        window.isAlwaysOnTop = true
        val lbBind = Label("未绑定")
        val tfCurrentChar = TextField()
        val tfMaxChar = TextField()
        val tfStart = TextField()
        val tfEnd = TextField()
        tfHwnd = TextField().apply { prefWidth = 105.0 }
        window.scene = Scene(VBox().addChildren(
                HBox().addChildren(
                        lbBind.apply { padding = Insets(3.0,0.0,0.0,0.0) },
                        tfHwnd.apply {
                            focus(false)
                            hint("请输入窗口句柄")
                        },
                        Button("绑定").clickBN {
                            if (tfHwnd.text.isNotEmpty()) {
                                lbBind.text = if (dm.bindDNF(tfHwnd.text.toInt())) "绑定成功" else "绑定失败"
                            } else {
                                lbBind.text = if (dm.bindDNF()) "绑定成功" else "绑定失败"
                            }
                            lbBind.setTextColor("#ff3333")
                            isBind.set(true)
                        },
                        Button("解绑").clickBN {
                            lbBind.text = if (dm.unBindWindow()) "解绑成功" else "解绑失败"
                            lbBind.setTextColor("#33ff33")
                            isBind.set(false)
                        },
                        Button("暂停绑定").click {
                            if((it.source as Button).text == "暂停绑定"){
                                dm.enableBind(0)
                                (it.source as Button).text = "恢复绑定"
                            } else {
                                dm.enableBind(1)
                                (it.source as Button).text = "暂停绑定"
                            }
                        }
                ).apply {
                    marginVb(20,20,20,0)
                    spacing = 10.0
                },
                HBox().addChildren(
                        Button("设置当前角色").clickBN {
                            currentCharacter.set(tfCurrentChar.text.toInt())
                        },
                        tfCurrentChar.apply { prefWidth = 50.0 },
                        Button("设置最大角色数").clickBN {
                            maxSize = tfMaxChar.text.toInt()
                        },
                        tfMaxChar.apply { prefWidth = 50.0 }
                ).apply {
                    marginVb(20,0,20,0)
                    spacing = 10.0
                },
                HBox().addChildren(
                        Button("购买门票").clickBN {
                            Thread { buyTickets(tfStart.text.toInt(), tfEnd.text.toInt()) }.start()
                        },
                        Button("转移金币").clickBN {
                            Thread { transferCoinMaterial(tfStart.text.toInt(), tfEnd.text.toInt()) }.start()
                        },
                        tfStart.apply{
                            hint("开始角色")
                            prefWidth = 70.0
                        },
                        tfEnd.apply {
                            hint("结束角色")
                            prefWidth = 70.0
                        }
                ).apply {
                    marginVb(20,0,20,0)
                    spacing = 10.0
                },
                Button("开始刷图").clickBN {
                    doScript(dm)
                }
        ).preSize(370, 180).apply {
            alignment = Pos.TOP_CENTER
            spacing = 20.0
        })
    }

    var currentStep = 1 //当前脚本执行到的步骤
    var inPage2 = false //是否处于选择角色的第二页

    /**
     * 执行脚本
     * @param dm Dispatch
     */
    private fun doScript(dm: Dispatch) {
        val lock = ReentrantLock()
        val cond = lock.newCondition()
        /*
        创建一个线程专门负责跳过对战表
         */
        service.submit { doJumpVsTable(lock, cond) }
        /*
        创建一个线程专门负责循环放技能
         */
        service.submit { doLoopSkills() }
        /*
        创建一个线程专门负责“再次挑战”和切换角色
         */
        service.submit { doChallengeAgainAndSwitchChar(lock, cond) }
        /*
        创建一个线程用于监测是否存在卡屏
         */
        service.submit {
            while (isBind.get())
                if (dm.isDisplayDead(0, 0, 100, 100, 25)) dm.beep(1000, 3000)
        }

    }

    /**
     * 再次挑战和切换角色
     */
    private fun doChallengeAgainAndSwitchChar(lock: ReentrantLock, cond: Condition) {
        while (isBind.get()) {
            lock.lock()
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
                //自动拾取
                if (tfHwnd.text.isNotEmpty()) {
                    dm.autoPick(tfHwnd.text.toInt())
                } else {
                    dm.autoPick()
                }
                s(100)
                for (i in 1..19) {
                    dm keyPress X
                    s(200.r())
                }
                //查找是否存在置灰的“再次挑战.bmp"
                //注意这里存在一个问题，万一一次没有找到这张图而再次挑战又无法执行，
                //那么上面的操作又将执行一次，因此必须避免这个问题
                //方案1：设置一个值记录刷图次数，当达到满次时就切换角色
                //方案2：重复多次执行这个函数，例如执行10次，如果10次内全是“-1|-1|-1”则再次挑战
                val result = (1..5).toList().map {
                    check(dm.findPic(770, 72, 810, 100, "再次挑战"))
                }
                if (result.contains(true)) {
                    //返回城镇
                    dm keyPress F12
                    s(3000)
                    sellEquipment()
                    s(100.r)
                    //前往选择角色界面
                    dm.goToCharacterPage()
                    s(1000)
                    while (!(1..3).toList().map { check(dm.findPic(685, 52, 709, 72, "选择角色")) }.contains(true)) {
                        dm.goToCharacterPage()
                        s(1000)
                    }
                    s(3000)
                    if (currentCharacter.incrementAndGet() > maxSize) {
                        //如果角色已经刷满了
                        //dm.exitOs(1)
                        dm.beep(1000, 1000)
                    } else {
                        //如果角色没有刷满
                        if (currentCharacter.get() in 13..24 && !inPage2) {
                            dm.selectCharacter(currentCharacter.get() % 12, true)
                            inPage2 = true
                        } else {
                            dm.selectCharacter(currentCharacter.get())
                        }
                        s(3000)
                        goToYellowDragon(false)
                        s(2000)
                        dm keyPress SPACE
                        needPauseSkills = false
                    }
                } else {
                    //再次挑战
                    dm keyPress B
                    s(5000)
                    needPauseSkills = false
                }
                currentStep = 1
                cond.signalAll()
                cond.await()
            }
            s(1000)
        }
        lock.unlock()
    }

    /**
     * 跳过对战表
     */
    private fun doJumpVsTable(lock: ReentrantLock, cond: Condition) {
        lock.lock()
        dm.selectCharacter(currentCharacter.get())
        s(3000.r)
        goToYellowDragon()
        dm keyPress SPACE //进入黄龙副本
        while (isBind.get()) {
            if (currentStep == 1) {
                //查找指定位置是否有“对.bmp”
                if (check(dm.findPic(420, 60, 470, 100, "对"))) {
                    s(1000)
                    //如果存在则按下SPACE跳过对战表
                    dm keyPress SPACE
                    //设置不需要暂停技能释放
                    needPauseSkills = false
                    currentStep = 2 //进入第2个步骤
                    cond.signalAll()
                    cond.await()
                }
                s(500)
            }
        }
        lock.unlock()
    }

    /**
     * 根据当前的角色索引决定如何循环释放技能
     */
    private fun doLoopSkills() {
        when (currentCharacter.get()) {
            in 1..7 -> dm.common()
            in 8..9 -> dm.skillASDFG(currentCharacter.get() == 8)
        }
    }

    /**
     * 从房间去往黄龙大会
     */
    private fun goToYellowDragon(haveClothes: Boolean = false) {
        dm.apply {
            walkDown(1800)
            walkLeft(if (!haveClothes) 2400 else 1800)
            walkUp(1500)
            walkRight(3500)
            walkDown(500)
            walkRight(2000)
        }
    }

    /**
     * 卖装备
     */
    private fun sellEquipment() {
        val result = (1..3).toList().map { dm.findPic(0, 0, 960, 600, "分解机") }
        val fenJieJi = result.find { it != "-1|-1|-1" }
        if (fenJieJi != null) {
            dm.doubleClick(fenJieJi)
        }
        s(100)
        dm.moveR(24, 24)
        s(200.r)
        dm.leftDoubleClick()
        s(200.r)
        //卖装备
        dm.doubleClick(577, 246) //点击装备TAB
        s(100.r)
        val startX = 568
        val zbGridWidth = 30
        val zbPosList = (0..15).toList().mapIndexed { i, it ->
            (startX + zbGridWidth * (i % 8)) to if (i > 7) 301 else 276
        }
        zbPosList.forEachIndexed { i, it ->
            dm.moveTo(it.first, it.second)
            s(100.r)
            dm.leftDown()
            s(100.r)
            dm.moveTo(425, 274)
            s(200.r)
            dm.leftUp()
            s(100.r)
            val confirm = (1..3).toList().map { dm.findPic(0, 0, 960, 600, "确认") }
            val confirmPos = confirm.find { it != "-1|-1|-1" }
            if (confirmPos != null) {
                dm.doubleClick(confirmPos)
            } else {
                return@forEachIndexed
            }
            s(500.r)
        }
        s(100.r)
        dm.keyPress(ESC)
    }

    /**
     * 购买黄龙大会入场门票
     * @param start Int 指定开始的角色索引，从1开始
     * @param end Int 指定结束的角色索引
     */
    private fun buyTickets(start: Int, end: Int) {
        var nextPage = false
        for (i in start..end) {
            when {
                i <= 12 -> {
                    dm.selectCharacter(i)
                }
                i < 24 -> {
                    if (!nextPage) {
                        nextPage = true
                        dm.selectCharacter(i % 12, nextPage)
                    } else {
                        dm.selectCharacter(i % 12)
                    }
                }
            }
            s(2000.r)
            //打开仓库
            dm.moveTo(245, 386)
            s(300.r)
            dm.leftDoubleClick()
            s(300.r)
            dm.moveTo(360, 128)
            s(300.r)
            dm.leftDoubleClick()
            s(300.r)
            dm.moveTo(287, 155)
            s(300.r)
            dm.leftDown()
            s(500)
            dm.enableRealMouse(1, 10, 100)
            dm.moveTo(570, 275)
            dm.leftUp()
            dm.enableRealMouse(0, 10, 100)
            s(500)
            dm.sendString(dm.findWindow("地下城与勇士", "地下城与勇士"), "100")
            s(500)
            dm.doubleClick(563, 275)
            s(500)
            dm.keyPress(ESC)
            s(100.r)
            dm.walkDown(2000)
            dm.walkLeft(5000)
            dm.walkDown(700)
            dm.walkRight(1000)
            dm.doubleClick(883, 334)
            s(100.r)
            dm.doubleClick(923, 387)
            s(100.r)
            dm.keyDown(SHIFT)
            s(100.r)
            dm.doubleClick(430, 170)
            s(100.r)
            dm.keyUp(SHIFT)
            s(100.r)
            dm.sendString(dm.findWindow("地下城与勇士", "地下城与勇士"), "20")
            s(500.r)
            dm.keyPress(ENTER)
            s(500.r)
            dm.leftDoubleClick()
            s(100.r)
            dm.keyPress(ESC)
            s(1000.r)
            dm.goToCharacterPage()
            s(2000.r)
        }
        dm.beep(600, 2000)
    }

    /**
     * 转移指定角色的金币和材料到账号仓库
     * @param start Int 指定开始的角色索引，从1开始
     * @param end Int 指定结束的角色索引
     */
    private fun transferCoinMaterial(start: Int, end: Int) {
        var nextPage = false
        for (i in start..end) {
            when {
                i <= 12 -> {
                    dm.selectCharacter(i)
                }
                i < 24 -> {
                    if (!nextPage) {
                        nextPage = true
                        dm.selectCharacter(i % 12, nextPage)
                    } else {
                        dm.selectCharacter(i % 12)
                    }
                }
            }
            s(2000.r)
            //打开仓库
            dm.moveTo(245, 386)
            s(300.r)
            dm.leftDoubleClick()
            s(300.r)
            dm.moveTo(360, 128)
            s(300.r)
            dm.leftDoubleClick()
            s(100.r)
            //存入金币
            dm.click(440, 393)
            s(100.r)
            dm.leftDoubleClick()
            s(500.r)
            dm.leftDoubleClick()
            //存入挑战书
            val tzsPos = (1..3).toList().map {
                dm.findPic(550, 260, 795, 473, "挑战书")
            }.find { check(it) }
            if (tzsPos != null) {
                dm.moveTo(tzsPos.toPoint().x, tzsPos.toPoint().y)
                s(100.r)
                dm.leftDown()
                s(100.r)
                dm.moveTo(270, 230)
                s(100.r)
                dm.leftUp()
                s(100.r)
                dm.leftDoubleClick()
            }
            s(100.r)
            //存入印章
            val yzPos = (1..3).toList().map {
                dm.findPic(550, 260, 795, 473, "印章")
            }.find { check(it) }
            if (yzPos != null) {
                dm.moveTo(yzPos.toPoint().x, yzPos.toPoint().y)
                s(100.r)
                dm.leftDown()
                s(100.r)
                dm.moveTo(270, 230)
                s(100.r)
                dm.leftUp()
                s(100.r)
                dm.leftDoubleClick()
            }
            s(100.r)
            //存入无尽的永恒
            val wjPos = (1..3).toList().map {
                dm.findPic(550, 260, 795, 473, "无尽的永恒")
            }.find { check(it) }
            if (wjPos != null) {
                dm.moveTo(wjPos.toPoint().x, wjPos.toPoint().y)
                s(100.r)
                dm.leftDown()
                s(100.r)
                dm.moveTo(270, 230)
                s(100.r)
                dm.leftUp()
                s(100.r)
                dm.leftDoubleClick()
            }
            //切换下个角色
            s(1000.r)
            dm keyPress ESC
            s(100.r)
            dm.goToCharacterPage()
        }
    }

    override fun stop() {
        //当应用停止后必须解除绑定，并关闭线程池
        isBind.set(false)
        dm.unBindWindow().pln("解除绑定结果：")
        service.shutdownNow()
        cacheService.shutdownNow()
        exitProcess(1)
    }

}
