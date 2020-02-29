package app.dnf.dh

import app.dnf.DnfUtils
import base.constant.*
import com.jacob.com.Dispatch
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import sample.base.BaseApp

@Resizable(false)
@AppTitle("DnfHelperApp")
class DnfHelperApp : BaseApp(), DnfUtils, Tab1Presenter, Tab2Presenter {

    private lateinit var dm: Dispatch

    override fun init(window: Window) {
        dm = initDmCom()
        dm.reg()
        val tabNameList = listOf("单步流程","找图测试")

        val tp = TabPane()
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
                        },
                        Button("窗口置顶").clickBN {
                            if(window.isAlwaysOnTop){
                                window.isAlwaysOnTop = false
                                (it.source as Button).text = "窗口置顶"
                            } else {
                                window.isAlwaysOnTop = true
                                (it.source as Button).text = "取消置顶"
                            }
                        }
                ).apply {
                    spacing = 10.0
                }.marginVb(0, 10, 0, 10),
                tp
        ))
        tp.tabs.addAll(
                tabNameList.toList().mapIndexed { index, i ->
                    Tab(tabNameList[index]).apply {
                        content = when(index){
                            0->tab1(dm, window, this)
                            else -> tab2(dm)
                        }
                        isClosable = false //是否可关闭
                    }
                }
        )
    }

    override fun stop() {
        dm.unBindWindow()
    }



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DnfHelperApp::class.java)
        }
    }

}