package base.tools

import base.constant.*
import base.utils.DmUtils
import javafx.collections.FXCollections
import javafx.scene.image.Image
import sample.base.BaseApp

/**
 * 大漠窗口绑定模式工具
 */
@AppTitle("大漠窗口绑定模式工具")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("bind_model_tool")
class BindModelTool : BaseApp(), DmUtils {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(BindModelTool::class.java)
        }
    }
    /*
      NORMAL,
        GDI,
        GDI2,
        DX2,
        DX3,
        DX
     */
    var model = "NORMAL"

    override fun init(window: Window) {
        val dm = initDmCom()
        dm.setPath("C:\\Users\\Administrator\\damo\\")
        btn("bind").click {
            dm.unBindWindow()
            val wh1 = dm.findWindow("LDPlayerMainFrame", "雷电模拟器")
            val wh2 = dm.findWindowEx(wh1, "RenderWindow", "TheRender")
            val result = dm.bindWindow(wh2, when(model){
                "NORMAL"->DmUtils.Display.NORMAL
                "GDI"->DmUtils.Display.GDI
                "GDI2"->DmUtils.Display.GDI2
                "DX"->DmUtils.Display.DX
                "DX2"->DmUtils.Display.DX2
                else->DmUtils.Display.DX3
            }, DmUtils.Mouse.WINDOWS, DmUtils.Keyboard.WINDOWS)
            alert(if(result) "绑定成功=$wh2" else "绑定失败")
        }

        cb("cb").apply {
            items = FXCollections.observableArrayList( "NORMAL",
                    "GDI",
                    "GDI2",
                    "DX",
                    "DX2",
                    "DX3")
            value = "NORMAL"
            setOnAction { ev ->
                model = cb("cb").selectionModel.selectedItem.toString()
            }
        }

        btn("capture").click {
            dm.capture(0,0,1280,720, "test.jpg")
            iv("iv").image = Image("file:C:\\Users\\Administrator\\damo\\test.jpg")
        }

    }

}