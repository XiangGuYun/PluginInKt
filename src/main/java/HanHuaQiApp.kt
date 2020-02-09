import base.Window
import base.utils.DmUtils
import base.utils.Win32Utils
import com.jacob.activeX.ActiveXComponent
import javafx.application.Application
import javafx.scene.image.Image
import sample.base.KotlinActivity

class HanHuaQiApp: KotlinActivity(), DmUtils, Win32Utils {

    var isStop = false

    override fun init(window: Window) {
        window.apply {
            title = "喊话器"
            isResizable = false
            icons.add(Image("image/sanguo.png"))
        }
        setContentView("layout/han_hua_qi")

//        val dm = initDmCom()

        btn("btnSend").click {

            alert(ActiveXComponent("dm.dmsoft").invoke("Ver").string
            )
//            ActiveXComponent("dm.dmsoft").`object`
//            isStop = false
//            val wh = dm.findWindow(null, "无标题 - 记事本")
//            val whChild = dm.findWindowEx(wh, null, null)
//             Thread{
//                 while (!isStop){
//                     println(dm.sendString2(whChild, "${ta("taSend").text}\n"))
//                     Thread.sleep(tf("tfTime").text.toLong())
//                 }
//             }.start()
        }

        btn("btnStop").click {
            isStop = true
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(HanHuaQiApp::class.java)
        }
    }

}