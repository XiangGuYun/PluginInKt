package app.dnf

import base.constant.*
import base.utils.Win32Utils
import com.jacob.com.Dispatch
import com.sun.jna.ptr.IntByReference
import sample.base.BaseApp

@AppTitle("练习")
@Resizable(false)
@AppIcon("dota.bmp")
@LayoutId("dnf_app")
class DnfApp : BaseApp(), DnfUtils,Win32Utils {

    private var mainThread: Thread? = null
    private lateinit var dm: com.jacob.com.Dispatch

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DnfApp::class.java)
        }
    }

    override fun init(window: Window) {
        window.x = 1600.0
        dm = initDmCom()
        println(getDmVersion())
        println("注册结果："+dm.reg())
        dm.setPath("D:/DM7.2/app.dnf")

        btn("bind").click{
            lb("lb1").text = if(dm.bindDNF()) "绑定成功" else "绑定失败"
        }

        btn("unbind").click {
            lb("lb1").text = if(dm.unBindWindow()) "解绑成功" else "解绑失败"
        }

        btn("enter").click{
            mainThread = Thread{
                dm.checkAndDoubleClick(dm.findPic(650,724,789,774, "练习模式.bmp", "101010", 0.9))
                s(1000)
                dm.checkAndDoubleClick(dm.findPic(553,150,776,246, "亡命杀镇.bmp", "101010", 0.9))
                while (!check(dm.findPic(0,751,77,818,"进入图1.bmp", "101010", 0.9))){
                    s(1000)
                }
                dm.buff()
                s(100)
                dm.run(DnfUtils.Dir.RIGHT, 1000)
                s(10)
                dm.keyPressDelay(Key.f, 10)
                dm.run(DnfUtils.Dir.RIGHT, 3100)
                dm.keyPress(Key.r)
                s(1000)
                dm.keepDownKeyRun(2000L, Key.right, Key.down)
                dm.run(DnfUtils.Dir.LEFT, 500)
                dm.run(DnfUtils.Dir.DOWN, 1200)
                dm.keyPress(Key.w)
                s(500)
                dm.run(DnfUtils.Dir.RIGHT, 1000)
                dm.keyPressDelay(Key.a, 10)
                dm.goToCornerAndNextRoom()
                //第三图
                dm.keepDownKeyRun(1000L, Key.right)
                dm.keepDownKey(3000L, Key.t)
                dm.keepDownKey(5250L, Key.right, Key.down)
                //第四图
                dm.keepDownKey(610L, Key.left)
                dm.keyPress(Key.e)
                s(2000)
                dm.keyPress(Key.f)
                s(1000)
                dm.keepDownKey(3000L, Key.left, Key.down)
                dm.keepDownKey(1100L, Key.up)
                //第五图
                dm.keyPressDelay(Key.h, 100) //45技能清怪
                dm.keepDownKey(2900L, Key.down)
                dm.keepDownKey(300L, Key.left)
                dm.keyPressDelay(Key.g, 100)
                dm.keepDownKey(1450L, Key.right)
                dm.keyPressDelay(Key.d, 100)
                dm.keepDownKeyRun(3000L, Key.right, Key.down)
                dm.keepDownKeyRun(600L, Key.up)
                dm.run(DnfUtils.Dir.RIGHT, 1000)
                //第六图
                dm.keepDownKey(1000L, Key.y)
                dm.keepDownKeyRun(5100L, Key.right, Key.down)
                //第七图
                dm.keepDownKey(800L, Key.right)
                dm.keyPressDelay(Key.r, 100)
                while (!check(dm.findPic(1100,200,1200,300, "是否继续.bmp", "101010", 0.9))){
                    s(1000)
                }
                dm.keyPressDelay(Key.f1, 1000)
            }
            mainThread?.start()
        }

        btn("return").click{
            Thread{
                dm.keyPress(Key.esc)
                s(500)
                dm.checkAndDoubleClick(dm.findPic(700,600,850,700,"返回城镇.bmp","101010",0.9))
            }.start()
        }

    }

    fun Dispatch.goToCornerAndNextRoom(){
        keepDownKeyRun(3000L, Key.right, Key.down)
        keepDownKeyRun(1500L, Key.up)
    }

    override fun stop() {
        mainThread?.interrupt()
        dm.unBindWindow()
        println("线程中断了")
    }

}