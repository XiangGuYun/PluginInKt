package base.jfx.layout

import base.constant.AppTitle
import base.constant.ScrollView
import base.constant.Window
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.layout.VBox
import sample.base.BaseApp

@AppTitle("ScrollPaneDemo")
class ScrollPaneDemo : BaseApp(){

    override fun init(window: Window) {
        window.scene = Scene(
                ScrollView(
                        VBox().addChildren(
                                (1..20).toList().mapIndexed { index, i ->
                                    if(index%2==0)
                                        Label("Label$i").preSize(300,100).align(Pos.CENTER)
                                    else
                                        Separator().preSize(300,1)
                                }
                        )
                ).preSize(315,600).apply {
                    vvalueProperty().addListener { observable, oldValue, newValue ->
                        newValue.pln()
                    }
                }
        )
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ScrollPaneDemo::class.java)
        }
    }

}