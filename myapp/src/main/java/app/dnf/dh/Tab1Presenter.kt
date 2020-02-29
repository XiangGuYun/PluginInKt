package app.dnf.dh

import app.dnf.DnfUtils
import com.jacob.com.Dispatch
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Window

interface Tab1Presenter : DnfUtils {

    fun tab1(dm: Dispatch, window: Window, tab:Tab): Node? {

        val rg1 = ToggleGroup()
        val rbWalk = RadioButton("走")
        val rbRun = RadioButton("跑")
        rg1.toggles.addAll(rbWalk, rbRun)
        //监听选中事件
        var isWalk = true
        rg1.selectedToggleProperty().addListener { _, _, newValue ->
            isWalk = (newValue as RadioButton).text == "走"
        }
        rbWalk.isSelected = true

        val rg2 = ToggleGroup()
        val rbLeft = RadioButton("左")
        val rbUp = RadioButton("上")
        val rbRight = RadioButton("右")
        val rbDown = RadioButton("下")
        val rbLeftUp = RadioButton("左上")
        val rbRightUp = RadioButton("右上")
        val rbLeftDown = RadioButton("左下")
        val rbRightDown = RadioButton("右下")
        rg2.toggles.addAll(rbLeft, rbUp, rbRight, rbDown, rbLeftUp, rbRightUp, rbLeftDown, rbRightDown)
        //监听选中事件
        var dir = 1
        rg2.selectedToggleProperty().addListener { _, _, newValue ->
            dir = when ((newValue as RadioButton).text) {
                "左" -> 1
                "上" -> 2
                "右" -> 3
                "下" -> 4
                "左上" -> 5
                "右上" -> 6
                "左下" -> 7
                else -> 8
            }
        }
        rbLeft.isSelected = true
        val tfTime = TextField()
        tfTime.text = "1000"
        val taRecord = TextArea().preSize(300, 100).marginVb(10, 10, 10, 10).apply {
            isWrapText = true
        }

        val box = VBox().addChildren(
                HBox().addChildren(
                        Label("动作方式"),
                        rbWalk,
                        rbRun
                ).apply {
                    spacing = 10.0
                    padding = Insets(10.0)
                },
                HBox().addChildren(
                        Label("动作方向"),
                        rbLeft,
                        rbUp,
                        rbRight,
                        rbDown
                ).apply {
                    spacing = 10.0
                    padding = Insets(10.0)
                },
                HBox().addChildren(
                        Label("动作方向"),
                        rbLeftUp, rbRightUp, rbLeftDown, rbRightDown
                ).apply {
                    spacing = 10.0
                    padding = Insets(10.0)
                },
                HBox().addChildren(
                        Label("动作时间").apply { padding = Insets(4.0, 0.0, 0.0, 0.0) },
                        tfTime,
                        Button("buff").clickBN {
                            dm.buff()
                        }
                ).apply {
                    spacing = 10.0
                    padding = Insets(10.0)
                },
                GridPane().apply {
                    setListV(7, listOf("Q", "W", "E", "R", "T", "Y", "一", "A", "S", "D", "F", "G", "H", "二").map {
                        Label(it).preSize(40, 40).align(Pos.CENTER).bgColor("#aaaaaa").click {
                            when ((it.source as Label).text) {
                                "一" -> "一绝".pln()
                                "二" -> "二觉".pln()
                                else -> {
                                    dm.keyDownChar((it.source as Label).text)
                                    taRecord.appendText("dm.keyDownChar(${(it.source as Label).text})\n")
                                }
                            }
                        }
                    })
                    vgap = 5.0
                    hgap = 5.0
                    marginVb(10, 0, 10, 0)
                },
                taRecord
        ).preSize(300, 340)

        window.scene.setHotKey(KeyCode.TAB,{
            if (isWalk) {
                when (dir) {
                    1 -> {
                        dm.walkLeft(tfTime.text.toInt())
                        taRecord.appendText("dm.walkLeft(${tfTime.text.toInt()})\n")
                    }
                    2 -> {
                        dm.walkUp(tfTime.text.toInt())
                        taRecord.appendText("dm.walkUp(${tfTime.text.toInt()})\n")
                    }
                    3 -> {
                        dm.walkRight(tfTime.text.toInt())
                        taRecord.appendText("dm.walkRight(${tfTime.text.toInt()})\n")
                    }
                    4 -> {
                        dm.walkDown(tfTime.text.toInt())
                        taRecord.appendText("dm.walkDown(${tfTime.text.toInt()})\n")
                    }
                    5 -> {
                        dm.walkLeftUp(tfTime.text.toInt())
                        taRecord.appendText("dm.walkLeftUp(${tfTime.text.toInt()})\n")
                    }
                    6 -> {
                        dm.walkRightUp(tfTime.text.toInt())
                        taRecord.appendText("dm.walkRightUp(${tfTime.text.toInt()})\n")
                    }
                    7 -> {
                        dm.walkLeftDown(tfTime.text.toInt())
                        taRecord.appendText("dm.walkLeftDown(${tfTime.text.toInt()})\n")
                    }
                    8 -> {
                        dm.walkRightDown(tfTime.text.toInt())
                        taRecord.appendText("dm.walkRightDown(${tfTime.text.toInt()})\n")
                    }
                }
            } else {
                when (dir) {
                    1 -> {
                        dm.runLeft(tfTime.text.toInt())
                        taRecord.appendText("dm.runLeft(${tfTime.text.toInt()})\n")
                    }
                    2 -> {
                        dm.runUp(tfTime.text.toInt())
                        taRecord.appendText("dm.runUp(${tfTime.text.toInt()})\n")
                    }
                    3 -> {
                        dm.runRight(tfTime.text.toInt())
                        taRecord.appendText("dm.runRight(${tfTime.text.toInt()})\n")
                    }
                    4 -> {
                        dm.runDown(tfTime.text.toInt())
                        taRecord.appendText("dm.runDown(${tfTime.text.toInt()})\n")
                    }
                    5 -> {
                        dm.runLeftUp(tfTime.text.toInt())
                        taRecord.appendText("dm.runLeftUp(${tfTime.text.toInt()})\n")
                    }
                    6 -> {
                        dm.runRightUp(tfTime.text.toInt())
                        taRecord.appendText("dm.runRightUp(${tfTime.text.toInt()})\n")
                    }
                    7 -> {
                        dm.runLeftDown(tfTime.text.toInt())
                        taRecord.appendText("dm.runLeftDown(${tfTime.text.toInt()})\n")
                    }
                    8 -> {
                        dm.runRightDown(tfTime.text.toInt())
                        taRecord.appendText("dm.runRightDown(${tfTime.text.toInt()})\n")
                    }

                }
            }
        })

        return box
    }

}