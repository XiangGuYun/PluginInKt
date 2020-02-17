package base.jfx.layout

import base.constant.Window
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import sample.base.BaseApp

class HBoxDemo : BaseApp() {
    override fun init(window: Window) {
        window.size(720, 480)
        window.scene = Scene(HBox().apply {
            addChildren(
                    Button("BTN1").preSize(window.width/3, window.height).click {
                        (it.source as Button).gone()
                    },
                    Button("BTN2").preSize(window.width/3, window.height).alpha(0.7),
                    Button("BTN3").preSize(window.width/3, window.height).click {
                        children[0].show()
                    }
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
            launch(HBoxDemo::class.java)
        }
    }
}