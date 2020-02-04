import base.*
import base.utils.DmUtils
import com.jacob.com.Dispatch
import sample.base.KotlinActivity

/**
 * 大漠识字（在指定区域内识别文字）和找字（寻找文字所在坐标）
 */
@AppTitle("DmFindWord")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("dm_find_word")
class DmWordFindTest : KotlinActivity(), DmUtils {

    private lateinit var dm: Dispatch

    override fun init(window: Window) {
        dm = initDmCom()
        dm.setPath("C:\\Users\\Administrator\\damo")
        dm.setDict(0, "dm_soft.txt")
        lb("bind").text = "绑定雷电模拟器是否成功？${dm.bindLDMonitor()}"
        //识字
        btn("rec").click {
            tf("field").text = dm.ocr(500,483,778,571, "ffffff-101010", 0.95)
        }
        //找字
        btn("find").click {
            tf("field").text = dm.findStrFast(500,483,778,571,
                    "登录游戏",
                    "ffffff-101010",
                    0.95)
        }
    }

    override fun stop() {
        dm.unBindWindow()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DmWordFindTest::class.java)
        }
    }
}