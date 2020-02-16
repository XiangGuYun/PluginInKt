package base.view

import base.constant.*
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.VBox
import sample.base.BaseApp

@AppTitle("TabPaneDemo")
class TabPaneDemo : BaseApp() {

    override fun init(window: Window) {
        val tp = TabPane().apply {
            tabs.addAll(
                    (1..4).toList().mapIndexed { index, i ->
                        Tab("TAB${i}").apply {
                            content = VBox().addChildren(
                                    (1..5).toList().mapIndexed { i, it ->
                                        Label("色彩$i").preSize(325, 100).bgColor(randomColor()).align(Pos.CENTER)
                                    }
                            )
                            graphic = FXIV("image/sanguo.png").fitSize(25, 15)
                            isClosable = false //是否可关闭
                            setOnSelectionChanged {
                                ("TAB事件"+(it.source as Tab).text+(it.source as Tab).isSelected).pln()
                            }
                            setOnClosed {
                                //监听关闭事件
                            }
                            setOnCloseRequest {
                                //监听关闭事件，优先于setOnClosed
                                it.consume() //消费事件，可阻止setOnClosed执行
                            }
                        }
                    }
            )
        }
        tp.widthProperty().addListener { observable, oldValue, newValue ->
            newValue.pln()
        }
        tp.selectionModel.select(1) //默认选中项
        tp.side = Side.LEFT //设置位置
        tp.isRotateGraphic = false
        //监听选择
        tp.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
            newValue.text.pln()
        }
        window.scene = Scene(VBox().addChildren(
                tp,
                Button("添加Tab").clickBtn {
                    tp.tabs.add(
                            Tab("NewTab").apply {
                                content = VBox().addChildren(
                                        (1..5).toList().mapIndexed { i, it ->
                                            Label("色彩$i").preSize(325, 100).bgColor(randomColor()).align(Pos.CENTER)
                                        }
                                )
                                graphic = FXIV("image/sanguo.png").fitSize(25, 15)
                            }
                    )
                }
        ))

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TabPaneDemo::class.java)
        }
    }

}