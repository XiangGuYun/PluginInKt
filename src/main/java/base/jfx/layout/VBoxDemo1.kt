package base.jfx.layout

import base.constant.AppTitle
import base.constant.LB
import base.constant.Window
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import javafx.scene.shape.Line
import sample.base.BaseApp

@AppTitle("VBoxDemo")
class VBoxDemo1 : BaseApp() {

    override fun init(window: Window) {
        window.scene = Scene(ScrollPane(
                VBox().addChildren(
                        (1..100).toList().mapIndexed { index, i ->
                            VBox().addChildren(
                                    LB("Item$i").textSize(24).preSize(300, 70).align(Pos.CENTER),
                                    Line(0.0,70.0,300.0,70.0).alpha(0.9)
                            )
                        }
                )
        ).preSize(320,600))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(VBoxDemo1::class.java)
        }
    }

}