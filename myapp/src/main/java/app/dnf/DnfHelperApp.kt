package app.dnf

import base.constant.*
import com.jacob.com.Dispatch
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.TextAlignment
import sample.base.BaseApp
import java.io.File

@Resizable(false)
@AppTitle("DnfHelperApp")
class DnfHelperApp : BaseApp(), DnfUtils {

    private lateinit var dm: Dispatch

    override fun init(window: Window) {
        dm = initDmCom()
        dm.reg()
        val tabNameList = listOf("测试走跑")

        val rg1 = TG()
        val rbWalk = RadioButton("走")
        val rbRun = RadioButton("跑")
        rg1.toggles.addAll(rbWalk, rbRun)
        //监听选中事件
        var isWalk = true
        rg1.selectedToggleProperty().addListener { _, _, newValue ->
            isWalk = (newValue as RadioButton).text == "走"
        }
        rbWalk.isSelected = true

        val rg2 = TG()
        val rbLeft = RadioButton("左")
        val rbUp = RadioButton("上")
        val rbRight = RadioButton("右")
        val rbDown = RadioButton("下")
        rg2.toggles.addAll(rbLeft, rbUp, rbRight, rbDown)
        //监听选中事件
        var dir = 1
        rg1.selectedToggleProperty().addListener { _, _, newValue ->
            dir = when ((newValue as RadioButton).text) {
                "左" -> 1
                "上" -> 2
                "右" -> 3
                else -> 4
            }
        }
        rbLeft.isSelected = true

        val tp = TabPane().apply {
            tabs.addAll(
                    tabNameList.toList().mapIndexed { index, i ->
                        Tab(tabNameList[index]).apply {
                            content = VBox().addChildren(
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
                                            Label("动作时间").apply { padding = Insets(4.0,0.0,0.0,0.0) },
                                            TextField()
                                    ).apply {
                                        spacing = 10.0
                                        padding = Insets(10.0)
                                    },
                                    Button("执 行").preSize(100,28).marginVb(100,30,100,0)
                            ).preSize(300, 240)
                            isClosable = false //是否可关闭
                        }
                    }
            )
        }
        tp.selectionModel.select(0) //默认选中项
        //监听选择
        tp.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
            newValue.text.pln()
        }
        val lbBind = Label("未绑定游戏")
        window.scene = Scene(VBox().addChildren(
                HBox().addChildren(
                        lbBind.apply {
                            marginHb(10, 4, 0, 0)
                        },
                        Button("绑定游戏").clickBN {
                            lbBind.text = if (dm.bindDNF()) "绑定成功" else "绑定失败"
                            lbBind.setTextColor("#ff3333")
                        },
                        Button("解绑游戏").clickBN {
                            lbBind.text = if (dm.unBindWindow()) "解绑成功" else "解绑失败"
                            lbBind.setTextColor("#33ff33")
                        }
                ).apply {
                    spacing = 10.0
                }.marginVb(0, 10, 0, 10),
                tp
        ))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DnfHelperApp::class.java)
        }
    }

}