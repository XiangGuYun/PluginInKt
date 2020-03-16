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
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.system.exitProcess

/**
 * 黄龙大会脚本
 */
@Style(StageStyle.UTILITY)
@AppTitle("YellowDragonApp")
class YellowDragonMultiApp : BaseApp(), DnfUtils {

    private lateinit var tfHwnd: TextField
    //该线程池用于灵活释放技能
    private lateinit var cacheService: ExecutorService
    //该线程池用于执行基本流程
    private lateinit var service: ExecutorService
    //当前的角色
    private var currentCharacter = AtomicInteger(1)
    //总共角色数量
    private val maxSize = 11
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
            launch(YellowDragonMultiApp::class.java)
        }
    }

    override fun init(window: Window) {
        initComponent()
        initView(window)
    }

    override fun stop() {
        //当应用停止后必须解除绑定，并关闭线程池
        isBind.set(false)
        dm.unBindWindow().pln("解除绑定结果：")
        service.shutdownNow()
        cacheService.shutdownNow()
        exitProcess(1)
    }

    private fun initComponent() {
        dm = initDmCom()
        dm.enableMouseSync(true, 2000)
        dm.enableKeypadSync(true, 2000)
        dm.enableRealKeypad(true)
        dm.enableRealMouse(2, 100, 200)
        service = Executors.newFixedThreadPool(10)
        cacheService = Executors.newCachedThreadPool()
        println("注册结果：" + dm.reg())
        println("路径设置结果：" + dm.setPath("${getUserDesktop()}/yellow_dragon"))
//        println("字库设置结果：" + dm.setDict(0, "字库.txt"))
    }

    private fun initView(window: Window) {
        window.isAlwaysOnTop = true
        val lbBind = Label("未绑定游戏")
        val tf = TextField()
        tfHwnd = TextField()
        window.scene = Scene(VBox().addChildren(
                lbBind,
                tfHwnd,
                Button("绑定窗口").clickBN {
                    if(tfHwnd.text.isNotEmpty()){
                        lbBind.text = if (dm.bindDNF(tfHwnd.text.toInt())) "绑定成功" else "绑定失败"
                    } else {
                        lbBind.text = if (dm.bindDNF()) "绑定成功" else "绑定失败"
                    }
                    lbBind.setTextColor("#ff3333")
                    isBind.set(true)
                },
                Button("解除绑定").clickBN {
                    lbBind.text = if (dm.unBindWindow()) "解绑成功" else "解绑失败"
                    lbBind.setTextColor("#33ff33")
                    isBind.set(false)
                },
                Button("开始刷图").clickBN {
                    doScript(dm)
                },
                tf.marginVb(20, 0, 20, 0),
                Button("设置当前角色").clickBN {
                    currentCharacter.set(tf.text.toInt())
                },
                Button("选择当前角色").clickBN {
                    dm.goToCharacterPage()
                    s(1000.r)
                    dm.nextCharacterPage()
                    s(1000.r)
                    dm.selectCharacter(6)
                }
        ).preSize(200, 400).apply {
            alignment = Pos.CENTER
            spacing = 20.0
        })
    }

    //游戏通关之后的处理线程
    private lateinit var threadPassGame: Thread
    //循环释放技能的线程
    private lateinit var threadLoopSkills: Thread
    //检查对战表的线程
    private lateinit var threadCheckVsTable: Thread
    var currentStep = 1 //当前脚本执行到的步骤
    var inPage2 = false

    /**
     * 执行脚本
     * @param dm Dispatch
     */
    private fun doScript(dm: Dispatch) {

        val lock = ReentrantLock()
        val cond = lock.newCondition()

        dm keyPress SPACE //进入黄龙副本

        /*
        创建一个线程专门负责跳过对战表
         */
        service.submit {
            threadCheckVsTable = Thread.currentThread()
            lock.lock()
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

        /*
        创建一个线程专门负责循环放技能
         */
        service.submit {
            threadLoopSkills = Thread.currentThread()
            while (isBind.get()) {
                if(!needPauseSkills){
                    dm keyPressIf A
                    sIf(300.r())
                    dm keyPressIf S
                    sIf(300.r())
                    dm keyPressIf D
                    sIf(300.r())
                }
            }
        }

        /*
        创建一个线程专门负责“再次挑战”和切换角色
         */
        service.submit {
            threadPassGame = Thread.currentThread()
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
                    if(tfHwnd.text.isNotEmpty()){
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
                        check(dm.findPic(770,72,810,100, "再次挑战"))
                    }
                    if (result.contains(true)) {
                        //返回城镇
                        dm keyPress F12
                        s(3000)
                        //前往选择角色界面
                        dm.goToCharacterPage()
                        s(1000)
                        while(!(1..3).toList().map { check(dm.findPic(685,52,709,72, "选择角色")) }.contains(true)){
                            dm.goToCharacterPage()
                            s(1000)
                        }
                        s(3000)
                        if (currentCharacter.incrementAndGet() > maxSize) {
                            //如果角色已经刷满了
                            dm.exitOs(1)
                        } else {
                            //如果角色没有刷满
                            if(currentCharacter.get() in 13..24 && !inPage2){
                                dm.selectCharacter(currentCharacter.get()%12, true)
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

        /*
        创建一个线程用于监测是否存在卡屏
         */
        service.submit {
            while (isBind.get()) {
                if (dm.isDisplayDead(0, 0, 100, 100, 10)) {
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

    infix fun Dispatch.keyPressIf(code:Int){
        if(!needPauseSkills){
            this.keyPress(code)
        }
    }

    fun sIf(time:Int){
        if(!needPauseSkills){
            s(time.r())
        }
    }

}
