package base.jfx.layout

import base.constant.AppTitle
import base.constant.Resizable
import base.constant.Style
import base.constant.Window
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.stage.StageStyle
import sample.base.BaseApp

@Style(StageStyle.UTILITY)
@Resizable(false)
@AppTitle("锚点布局")
class AnchorPaneDemo : BaseApp() {
    override fun init(window: Window) {
        window.width = 800.0
        window.height = 400.0
        val ap = AnchorPane()
        ap.addChildren(
                Button("BTN1").apply {
                    marginAp(20, 20, window.width/3*2, 20)
                    bgShape("#ff6666", 10, 0)
                    textSize(20)
                },
                Button("BTN2").apply {
                    marginAp(window.width/3, 20, window.width/3, 20)
                    bgShape("#6666ff", 10, 0)
                    textSize(20)
                },
                Button("BTN3").apply {
                    marginAp(window.width/3*2, 20, 20, window.height/2-10)
                    bgShape("#66ff66", 10, 0)
                    textSize(20)
                },
                Button("BTN3").apply {
                    marginAp(window.width/3*2, window.height/2-10, 20, 20)
                    bgShape("#666666", 10, 0)
                    textSize(20)
                }
        )
        ap.padding = Insets(10.0)
        window.scene = Scene(ap)
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(AnchorPaneDemo::class.java)
        }
    }
}