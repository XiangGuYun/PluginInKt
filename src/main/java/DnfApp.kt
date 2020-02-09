import base.*
import base.utils.DmUtils
import base.utils.Key
import base.utils.Win32Utils
import dnf.DnfUtils
import dota.DotaApp
import javafx.application.Platform
import sample.base.KotlinActivity
import java.awt.Point
import javax.xml.ws.Dispatch

@AppTitle("练习")
@Resizable(false)
@AppIcon("dota.bmp")
@LayoutId("dnf_app")
class DnfApp : KotlinActivity(), DnfUtils {

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
        println("注册结果："+dm.reg())
        dm.setPath("D:/DM7.2/dnf")
//        dm.setDict(0, "dnf.txt")

        btn("bind").click{
            lb("lb1").text = if(dm.bindDNF()) "绑定成功" else "绑定失败"
        }

        btn("unbind").click {
            lb("lb1").text = if(dm.unBindWindow()) "解绑成功" else "解绑失败"
        }

        btn("enter").click{
            Thread{
                dm.checkAndDoubleClick(dm.findPic(650,724,789,774, "练习模式.bmp", "101010", 0.9))
                s(1000)
                dm.checkAndDoubleClick(dm.findPic(553,150,776,246, "亡命杀镇.bmp", "101010", 0.9))
                dm.room1()
            }.start()
        }

        btn("buff").click{
            alert(dm.findPic(0,0,1309, 818, "通关.bmp", "101010", 0.8))
        }

        btn("return").click{
            Thread{
                dm.keyPress(Key.esc)
                s(500)
                dm.checkAndDoubleClick(dm.findPic(700,600,850,700,"返回城镇.bmp","101010",0.9))
            }.start()
        }



    }

    override fun stop() {
        dm.unBindWindow()
    }

}