package base.jfx.layout

import base.constant.Window
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import sample.base.BaseApp

class BorderPaneDemo : BaseApp() {
    override fun init(window: Window) {
        window.size(720, 480)
        window.scene = Scene(BorderPane().apply {
            top = Label("标题").textSize(20).align(Pos.CENTER).preSize(window.width/4,50)
                    .bgColor("#ff6666").marginBp(5,5,5,5)
            bottom = AnchorPane().preSize(window.width,50).bgColor("#66fe71")
                    .marginBp(5,5,5,5)
            center = AnchorPane().bgColor("#666666")
            left = AnchorPane().preSize(50,window.height).bgColor("#73fdc1")
                    .marginBp(5,5,5,5)
            right = AnchorPane().preSize(50,window.height).bgColor("#9811fe")
                    .marginBp(5,5,5,5)
            padding = Insets(5.0,5.0,5.0,5.0)
        })
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(BorderPaneDemo::class.java)
        }
    }
}