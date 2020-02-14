import base.constant.*
import base.utils.DmUtils
import base.utils.Win32Utils
import com.jacob.com.Dispatch
import com.sun.jna.platform.win32.Shell32
import com.sun.jna.platform.win32.User32
import javafx.application.Platform
import sample.base.BaseApp

@AppTitle("大漠练习1")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("dmtest1")
class DmApp : BaseApp(), DmUtils, Win32Utils {

    private lateinit var dm: Dispatch

    override fun init(window: Window) {
        dm = initDmCom()
        dm.setPath(DESKTOP)

        btn("btnBindBaidu").click {
            val result = dm.bindWindow(132112, DmUtils.Display.GDI,
                    DmUtils.Mouse.NORMAL, DmUtils.Keyboard.NORMAL)
            alert("bind is success? $result")
        }

        btn("btnCapture").click{
            alert("截取是否成功? ${dm.capture(0,0,300,300, "testCapture.jpg")}")
        }

        btn("btnMouse").click {
            dm.setClipboard("你好呀，百度")
            Thread{
                dm.moveTo(650, 428)
                Thread.sleep(100)
                dm.leftClick()
                Thread.sleep(100)
                dm.keyDown(Key.ctrl)
                Thread.sleep(100)
                dm.keyPress(Key.v)
                Thread.sleep(100)
                dm.keyUp(Key.ctrl)
            }.start()
        }

        btn("btnBindLeiDian").click{
            val wh1 = dm.findWindow("LDPlayerMainFrame", "雷电模拟器")
            val wh2 = dm.findWindowEx(wh1, "RenderWindow", "TheRender")
            val result = dm.bindWindow(wh2, DmUtils.Display.NORMAL, DmUtils.Mouse.WINDOWS, DmUtils.Keyboard.WINDOWS)
            alert("绑定是否成功？$result")
        }


        btn("btnFIndPic1").click {
           Thread{
               val result = dm.findPic(0, 0, 1280, 720, "pic1.bmp", "202020", 0.9, DmUtils.DIR.LR_TB)
               Platform.runLater {
                   alert(result)
               }
               val arr = result.split("|")
               Thread.sleep(100)
               dm.moveTo(arr[1].toInt(), arr[2].toInt())
               Thread.sleep(100)
               dm.leftClick()
           }.start()
        }

        btn("btnClear").click {
            alert("清除了 ${Shell32.INSTANCE.SHEmptyRecycleBin(User32.INSTANCE.FindWindow(null, "大漠练习1"), null, 0)}")
        }

    }

    override fun stop() {
        super.stop()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DmApp::class.java)
        }
    }

}