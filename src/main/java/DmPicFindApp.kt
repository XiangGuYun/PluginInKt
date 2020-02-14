import base.constant.*
import base.utils.DmUtils
import com.jacob.com.Dispatch
import javafx.application.Platform
import sample.base.BaseApp

/**
 * 大漠找图
 */
@AppTitle("DmFindPic")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("dm_find_pic")
class DmPicFindApp : BaseApp(), DmUtils {

    private lateinit var dm: Dispatch

    override fun init(window: Window) {
        dm = initDmCom()
        dm.setPath("C:\\Users\\Administrator\\damo")

        btn("bindLD").click{
            alert("绑定雷电模拟器是否成功？${dm.bindLDMonitor()}")
        }

        btn("findPic").click {
            Thread{
                val result = dm.findPic(0, 0, 1280, 720, "dota3.bmp", "202020", 0.9, DmUtils.DIR.RL_TB)
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

    }

    override fun stop() {
        dm.unBindWindow()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DmPicFindApp::class.java)
        }
    }
}