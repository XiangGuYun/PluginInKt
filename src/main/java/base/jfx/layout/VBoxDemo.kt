package base.jfx.layout

import base.constant.Window
import base.utils.DmUtils
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import sample.base.BaseApp

class VBoxDemo : BaseApp() {
    override fun init(window: Window) {

        window.size(480, 720)
        window.scene = Scene(VBox().apply {
            addChildren(
                    Button("BTN1").preSize(window.width, window.height/3),
                    Button("BTN2").preSize(window.width, window.height/3),
                    Button("BTN3").preSize(window.width, window.height/3)
            )
            padding = Insets(20.0)
            spacing = 10.0
            children[0].margin(10,10,10,10)
            children[2].margin(10,10,10,10)
        })
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(VBoxDemo::class.java)
        }
    }
}