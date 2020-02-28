package app.dnf.yd

import app.dnf.DnfUtils
import base.constant.AppTitle
import base.constant.Key
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
import java.util.concurrent.Executors

/**
 * 黄龙大会脚本
 */
@Style(StageStyle.UTILITY)
@AppTitle("YellowDragonApp")
class YellowDragonApp : BaseApp(), DnfUtils, SkillPresenter {

    override fun init(window: Window) {
        window.isAlwaysOnTop = true
        dm = initDmCom()
        println("注册结果：" + dm.reg())
        println("路径设置结果：" + dm.setPath("""${DESKTOP}yellow_dragon"""))
        val lbBind = Label("未绑定游戏")
        val tfTime = TextField()
        val tfDir = TextField()
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
                Button("返回城镇").clickBN {
                    dm.goToCharacterPage()
                },
                Button("选择角色7").clickBN {
                    dm.selectCharacter(7)
                },
                Button("去黄龙大会").clickBN {
                    goToYellowDragon()
                },
                Button("打开账号金库").clickBN {
                }
        ).preSize(200, 400).apply {
            alignment = Pos.CENTER
            spacing = 20.0
        })
    }

    private lateinit var dm: Dispatch
    private var isJieBa = true
    private var needPauseSkills = false //当此标志为true时，暂停技能循环释放
    private var isBind = false

    private fun doScript(dm: Dispatch) {
        dm.keyPress(Key.space)
        val service = Executors.newFixedThreadPool(4)
        //创建一个线程专门负责“再次挑战”
        service.submit {
            while (isBind) {
                val result = dm.findPic(1150, 200, 1270, 240, "返回城镇", "101010", 0.9)
                if (result != "-1|-1|-1") {
                    needPauseSkills = true
                    if (isJieBa) {
                        dm.keyPress(Key.space)
                        s(100)
                        dm.keyPress(Key.space)
                    }
                    s(2000) //这个时间不能短于一轮技能的时间总和
                    dm.autoPick()
                    s(100)
                    for (i in 1..19) {
                        dm.keyPress(Key.x)
                        s(200)
                    }
                    dm.keyPress(Key.b)
                    s(4000)
                    needPauseSkills = false
                }
                s(2000)
            }
        }
        //创建一个线程专门负责跳过对战表
        service.submit {
            while (isBind) {
                val result = dm.findPic(630, 80, 810, 160, "对战表", "101010", 0.9)
                if (result != "-1|-1|-1") {
                    dm.keyPress(Key.space)
                    //创建一个线程专门负责循环放技能
                    service.submit {
                        while (isBind && !needPauseSkills) {
                            dm.common()
                        }
                    }
                }
                s(2000)
            }
        }
    }

    override fun stop() {
        dm.unBindWindow().pln("解除绑定结果：")
        super.stop()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(YellowDragonApp::class.java)
        }
    }

    private fun goToYellowDragon(haveClothes: Boolean = true) {
        Thread {
            dm.walkDown(1800)
            dm.walkLeft(if (!haveClothes) 2100 else 1800)
            dm.walkUp(1000)
            dm.walkRight(3500)
            dm.walkDown(500)
            dm.walkRight(500)
        }.start()
    }

    fun testWalk(time: Int, dir: Int) {
        dm.keepDownKey(time.toLong(), when (dir) {
            1 -> Key.left
            2 -> Key.up
            3 -> Key.right
            else -> Key.down
        })
    }

}
