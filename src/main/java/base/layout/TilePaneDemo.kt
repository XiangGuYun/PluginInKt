package base.layout

import base.constant.Window
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Rectangle
import sample.base.BaseApp
import javax.swing.GroupLayout

/**
 * 类似于FlowPane
 */
class TilePaneDemo : BaseApp() {
    override fun init(window: Window) {
        window.size(720, 480)

        val labelList = (1..24).toList().mapIndexed { index, item ->
            Label("标签${index}").preSize((30..100).shuffled().last(), (30..100).shuffled().last()).align(Pos.CENTER)
                    .bgColor(randomColor())
                   .marginFp(5,5,5,5)
        }
        window.scene = Scene(TilePane().apply {
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
            launch(TilePaneDemo::class.java)
        }
    }
}