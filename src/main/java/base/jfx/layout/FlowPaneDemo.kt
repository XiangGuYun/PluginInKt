package base.jfx.layout

import base.constant.Window
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import sample.base.BaseApp

class FlowPaneDemo : BaseApp() {
    override fun init(window: Window) {
        window.size(720, 480)
        val labelList = (1..20).toList().mapIndexed { index, item ->
            Label("标签${index}").preSize(70, (60..120).shuffled().last()).align(Pos.CENTER)
                    .bgColor(randomColor()).marginFp(5,5,5,5)
        }
        window.scene = Scene(FlowPane().apply {
            children.addAll(labelList)
            padding = Insets(5.0,5.0,5.0,5.0)
            alignment = Pos.CENTER
            hgap = 5.0
            vgap = 5.0
            orientation = Orientation.VERTICAL
        })
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(FlowPaneDemo::class.java)
        }
    }
}