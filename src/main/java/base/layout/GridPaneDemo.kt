package base.layout

import base.constant.Resizable
import base.constant.Window
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.*
import sample.base.BaseApp
import javax.swing.GroupLayout

@Resizable(false)
class GridPaneDemo : BaseApp() {
    override fun init(window: Window) {
        window.size(720, 480)
        val gp = GridPane()
        val labelList = (1..100).toList().mapIndexed { index, i ->
            Label("标签$index").preSize(window.width/10,window.height/10).align(Pos.CENTER).bgColor(randomColor())
        }
        gp.setListV(10, labelList)
        gp.hgap =1.0
        gp.vgap = 1.0
        gp.padding = Insets(10.0)
        window.scene = Scene(gp)
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(GridPaneDemo::class.java)
        }
    }
}