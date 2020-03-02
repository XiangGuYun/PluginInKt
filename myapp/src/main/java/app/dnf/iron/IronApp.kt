package app.dnf.iron

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
 * 钢铁脚本编写思路
 * 首先必须清楚一点，图色脚本搬砖肯定是慢于手动搬砖的，但是优势是全自动多线程且非常稳定，因此需要做到的是尽量优化脚本来缩短流程耗时。
 * DNF的角色位置是随机的，再加上其它不确定性，因此很难有一条写死的脚本可以一直前往下一个房间，而且技能释放完时可以仍有怪存活，
 * 因此必须要制定一套算法来解决这些问题。
 * 1.角色技能需要分为两类，一类是固定清怪技能，还有一类用于清除残存的怪。
 * 2.当执行某个房间的脚本时，务必根据右上角小地图来检查角色当前是否位于对应的房间，如果是则执行脚本，否则就需要循环判断角落是否处于卡屏
 *   状态，若是则阻塞脚本线程来寻找房间入口。
 * 3.根据房间入口的位置来决定寻找的路线，寻找的同时如果有残存的怪（可以根据小地图或者房间灯泡来判断）也会被吸引过来，
 *   如果存在，可以一边走动，一边用技能来消灭余怪。
 * 4.找入口时，有时可以根据左右入口上面或下面以及上下路口的左边或右边的截图来判断当前角色卡位的方向。
 * 5.找到入口后，需要添加一小段额外脚本来衔接当前的房间脚本。
 */
@Style(StageStyle.UTILITY)
@AppTitle("IronApp")
class IronApp : BaseApp(), DnfUtils {

    //该线程池用于执行基本流程
    private lateinit var service: ExecutorService
    //当前的角色
    private var currentCharacter = 1
    //总共角色数量
    private val maxSize = 10
    //大漠组件
    private lateinit var dm: Dispatch

    companion object {
        //当此标志为true时，暂停技能循环释放
        var needPauseSkills = false
        //是否处于绑定大漠状态
        var isBind = false

        @JvmStatic
        fun main(args: Array<String>) {
            launch(IronApp::class.java)
        }
    }

    private fun initComponent() {
        dm = initDmCom()
        service = Executors.newFixedThreadPool(10)
        println("注册结果：" + dm.reg())
        println("路径设置结果：" + dm.setPath("${DESKTOP}iron"))
    }

    override fun init(window: Window) {
        initComponent()
        initView(window)
    }

    override fun stop() {
        dm.backToTown()
        //当应用停止后必须解除绑定，并关闭线程池
        isBind = false
        dm.unBindWindow().pln("解除绑定结果：")
        service.shutdownNow()
    }

    private fun initView(window: Window) {
        window.isAlwaysOnTop = true
        val lbBind = Label("未绑定")
        val tf = TextField()

        lbBind.text = if (dm.bindDNF()) "绑定成功" else "绑定失败"
        lbBind.setTextColor("#ff3333")
        isBind = true

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
                    service.shutdownNow()
                },
                Button("开始刷图").clickBN {
                    isBind = true
                    doScript(dm)
                },
                tf.marginVb(20, 0, 20, 0),
                Button("返回城镇").clickBN {
                    isBind = false
                    dm.backToTown()
                }
        ).preSize(200, 400).apply {
            alignment = Pos.CENTER
            spacing = 20.0
        })
    }

    var pressH = false
    var pauseZ = true
    var step = 0
    var isLeftTop = true

    private fun doScript(dm: Dispatch) {
        dm.walkLeft(100)
        dm.walkRight(1100)
        s(1200)
        dm.keyPress(F10)
        s(1200)
        dm keyPress SPACE
        //创建一个线程专门负责“再次挑战”
        service.submit {
            while (isBind) {
                if (check(dm.findPic(122, 533, 143, 552, "进图"))) {
                    dm keyPress W
                    if (!pressH) {
                        //加一绝
                        dm keyPress H
                        pressH = true
                    }
                    s(800)
                    dm keyPress Y //旋刃冲击清理一图怪
                    s(1000) //1500
                    dm.runRight(1500)
                    dm.runLeft(100)
                    s(100)
                    dm.runUp(500)
                    s(100)
                    dm.runRight(600)
                    dm.runDown(150)
                    pauseZ = false
                    s(1500)
                    dm keyPress E //雷光清理二图怪
                    s(1000)
                    pauseZ = true
                    s(1000)
                    s(100)
                    dm.runRight(2000)
                    dm.runLeft(200)
                    dm.runUp(200)
                    dm.runRight(200)
                    dm.runRightDown(500)
                    var needMoreActTo3 = false
                    //判断是否进入第三图
                    while (!check(dm.findPic(880, 45, 935, 67, "第三图", "101010", 0.7))) {
                        if (dm.checkIsDeadDisplay(isLeftTop, 1)) {
                            needMoreActTo3 = true
                            if (isLeftTop) {
                                dm.walkLeft(300)
                                dm.walkDown(350)
                                dm.keyPress(S)
                                dm.runRight(500)
                                dm.keyPress(R)
                                step++
                                if (step >= 3) {
                                    step = 0
                                    isLeftTop = false
                                }
                            } else {
                                dm.walkLeft(300)
                                dm.walkUp(350)
                                dm.keyPress(S)
                                dm.runRight(500)
                                dm.keyPress(R)
                                step++
                                if (step >= 3) {
                                    step = 0
                                    isLeftTop = true
                                }
                            }
                        }
                        s(100)
                    }
                    step = 0
                    if (needMoreActTo3) {
                        dm.runRightDown(500)
                    }

                    //剑刃风暴清理三图怪
                    for (i in 1..5) {
                        dm.keyPress(G)
                        s(100)
                    }
                    dm.runDown(1700)
                    //检查是否进入了第四图
                    var needMoreActTo4 = false
                    while (!check(dm.findPic(900, 48, 937, 84, "第四图"))) {
                        needMoreActTo4 = true
                        if (check(dm.findPic(0, 0, 70, 30, "第三图左上角", "101010", 0.8))) {
                            isLeftTop = false
                        }
                        if(check(dm.findPic(0,0,83,35, "第三图左上角1", "101010", 0.8))){
                            isLeftTop = false
                        }
                        if (dm.checkIsDeadDisplay(isLeftTop, 1)) {
                            if (check(dm.findPic(908, 147, 960, 192, "第三图卡右", "101010", 0.8))) {
                                dm.runUp(300)
                                dm.runLeft(450)
                                dm.runDown(1000)
                            }
                            if(check(dm.findPic(659,425,821,525, "第三图先往右", "101010", 0.7))){
                                isLeftTop = false
                            }
                            if (isLeftTop) {
                                dm.runLeft(300)
                                dm.runDown(300)
                                dm.keyPress(if (step % 2 == 0) S else R)
                                dm.keyPress(X)
                                step++
                                if (step >= 3) {
                                    step = 0
                                    isLeftTop = false
                                }
                            } else {
                                dm.runRight(300)
                                dm.runDown(300)
                                dm.keyPress(if (step % 2 == 0) S else R)
                                dm.keyPress(X)
                                step++
                                if (step >= 3) {
                                    step = 0
                                    isLeftTop = true
                                }
                            }

                        }
                        s(100)
                    }
                    step = 0
                    if (needMoreActTo4) {
                        dm.runDown(300)
                    }
                    dm.runRight(300)
                    dm.walkLeft(80)
                    dm.keyPress(H)
                    s(2500)
                    dm.runLeft(2000)
                    isLeftTop = true
                    var needAddActTo5 = false
                    //检查是否进入了第五图
                    while (!check(dm.findPic(895, 45, 935, 85, "第五图", "101010", 0.8))) {
                        if (dm.checkIsDeadDisplay(true, 1)) {
                            needAddActTo5 = true
                            isLeftTop = check(dm.findPic(102,243,228,374,"第四图卡在下面","101010",0.7))
                            if (isLeftTop) {
                                dm.runRight(100)
                                dm.runUp(100)
                                dm.runLeft(300)
                                dm.keyPress(if (step % 2 == 0) S else R)
                                step++
                                if (step >= 4) {
                                    step = 0
                                    isLeftTop = false
                                }
                            } else {
                                dm.runRight(100)
                                dm.runDown(100)
                                dm.runLeft(300)
                                dm.keyPress(if (step % 2 == 0) S else R)
                                step++
                                if (step >= 4) {
                                    step = 0
                                    isLeftTop = true
                                }
                            }
                        } else {
                            "没有卡屏".pln()
                        }
                        s(100)
                    }
                    println("进入了第五图")
                    if (needAddActTo5) {
                        dm.runLeft(1000)
                    }
                    s(300)
                    //剑刃风暴清理五图怪
                    for (i in 1..10) {
                        dm.keyPress(G)
                        s(100)
                    }
                    dm.runLeft(300)
                    dm.runDown(2000)
                    //检查是否进入了第六图
                    isLeftTop = !needAddActTo5
                    var needMoreActTo6 = false
                    while (!check(dm.findPic(895, 63, 920, 102, "第六图", "101010", 0.7))) {
                        if (dm.checkIsDeadDisplay(isLeftTop, 1)) {
                            needMoreActTo6 = true
                            if (isLeftTop) {
                                dm.runLeft(200)
                                dm.runDown(200)
                                dm.keyPress(if (step % 2 == 0) S else R)
                                dm.keyPress(X)
                                step++
                                if (step >= 4) {
                                    step = 0
                                    isLeftTop = false
                                }
                            } else {
                                dm.runRight(200)
                                dm.runDown(200)
                                dm.keyPress(if (step % 2 == 0) S else R)
                                dm.keyPress(X)
                                step++
                                if (step >= 4) {
                                    step = 0
                                    isLeftTop = true
                                }
                            }

                        }
                        s(100)
                    }
                    step = 0
                    if (needMoreActTo6) {
                        dm.runDown(500)
                        dm.runLeft(500)
                        dm.runRight(80)
                    }
                    pauseZ = false
                    dm.keepDownKey(1500L, Q)
                    pauseZ = true
                    dm.walkRight(200)
                    dm.keyPress(Y)
                    dm.runRight(2000)
                    dm.runUp(500)
                    dm.runRight(1500)
                    dm.runDown(500)

                    //检查是否进入了第七图
                    isLeftTop = true
                    var needMoreActTo7 = false
                    while (!check(dm.findPic(916, 82, 953, 105, "第七图", "101010", 0.7))) {
                        if (dm.checkIsDeadDisplay(isLeftTop, 1)) {
                            needMoreActTo7 = true
//                            if(!check(dm.findPic(727,397,916,492, "第六图往上走","101010",0.7))){
//                                isLeftTop = true
//                            }
                            if (!isLeftTop) {
                                dm.runLeft(200)
                                dm.keyPress(S)
                                dm.keyPress(X)
                                dm.runDown(300)
                                dm.runRight(400)
                                dm.keyPress(R)
                                dm.keyPress(X)
                                step++
                                if (step >= 3) {
                                    step = 0
                                    isLeftTop = true
                                }
                            } else {
                                dm.runLeft(200)
                                dm.keyPress(S)
                                dm.keyPress(X)
                                dm.runUp(300)
                                dm.runRight(400)
                                dm.keyPress(R)
                                dm.keyPress(X)
                                step++
                                if (step >= 3) {
                                    step = 0
                                    isLeftTop = false
                                }
                            }

                        }
                        s(100)
                    }
                    step = 0
                    if (needMoreActTo7) {
                        dm.runRight(800)
                        dm.runDown(500)
                    }

                    //剑刃风暴清理七图怪
                    for (i in 1..5) {
                        dm.keyPress(G)
                        s(100)
                    }
                    dm.runUp(100)
                    dm.runRight(2000)

                    //检查是否进入了第Boss图
                    isLeftTop = false
                    while (!check(dm.findPic(914, 82, 956, 101, "Boss图", "101010", 0.7))) {
                        if (dm.checkIsDeadDisplay(isLeftTop, 1)) {
                            if (isLeftTop) {
                                dm.runLeft(100)
                                dm.keyPress(S)
                                dm.runDown(300)
                                dm.runRight(600)
                                dm.keyPress(R)
                                step++
                                if (step >= 4) {
                                    step = 0
                                    isLeftTop = false
                                }
                            } else {
                                dm.runLeft(100)
                                dm.keyPress(S)
                                dm.runUp(300)
                                dm.runRight(600)
                                dm.keyPress(R)
                                step++
                                if (step >= 4) {
                                    step = 0
                                    isLeftTop = true
                                }
                            }

                        }
                        s(100)
                    }
                    step = 0
                    dm.runRightDown(800)
//                    dm.keyPress(UP)
//                    s(50)
//                    dm.keyPress(DOWN)
//                    s(50)
//                    dm.keyPress(SPACE)
                    dm.walkLeft(50)
                    dm.keyPress(T)
                    dm.beep(1000, 2000)
                    s(5000)
//                    dm.keyPress(F12)
                    pressH = false
                }
            }
            s(1000)
        }

        service.submit {
            while (isBind && !pauseZ) {
                dm keyPress Z
                s(100)
            }
        }

    }

}