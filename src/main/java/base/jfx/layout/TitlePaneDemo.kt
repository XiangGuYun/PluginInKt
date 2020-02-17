package base.jfx.layout

import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Accordion
import javafx.scene.control.Label
import javafx.scene.control.TitledPane
import javafx.scene.layout.VBox
import sample.base.BaseApp

/**
 * Accordion+TitlePane
 * 实现收缩和展开的面板
 */
@AppTitle("TitlePaneDemo")
class TitlePaneDemo : BaseApp(){

    override fun init(window: Window) {
        val ac = Accordion().apply {
            expandedPaneProperty().addListener { _, _, newValue ->
                if(newValue != null) ("Acc   "+ (newValue as TitledPane).text).pln()
            }
        }
        ac.panes.addAll(
                (1..3).toList().mapIndexed { i, it->
                    TitledPane("色彩学${i+1}", VBox().addChildren(
                            (1..2).toList().mapIndexed { i,it->
                                Label("色彩$i").preSize(200,100).bgColor(randomColor()).align(Pos.CENTER)
                            }
                    )).apply {
                        isExpanded = true
                        isCollapsible = true
                        expandedProperty().addListener { _, _, newValue ->
                            ("TP"+if(newValue) "打开" else "关闭").pln()
                        }
                    }
                }
        )
        window.height = 300.0
        window.scene = Scene(ac)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TitlePaneDemo::class.java)
        }
    }

}