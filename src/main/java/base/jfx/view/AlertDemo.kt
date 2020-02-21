package base.jfx.view

import base.constant.AppIcon
import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import sample.base.BaseApp

/**
 * Dialog本质是对Window的一种定制化，类似于Android的AlertDialog
 */
@AppIcon("sanguo.png")
@AppTitle("AlertDemo")
class AlertDemo : BaseApp(){

    override fun init(window: Window) {


        window.scene = Scene(
                VBox().addChildren(
                        Button("确认弹窗").clickBN {
                            Alert(Alert.AlertType.CONFIRMATION).apply {
                                headerText = null
                            }.show()
                        }.preSize(500,60),
                        Button("报错弹窗").clickBN {
                            Alert(Alert.AlertType.ERROR).apply {
                                headerText = null
                            }.show()
                        }.preSize(500,60),
                        Button("信息弹窗").clickBN {
                            Alert(Alert.AlertType.INFORMATION).apply {
                                headerText = null
                            }.show()
                        }.preSize(500,60),
                        Button("空弹窗").clickBN {
                            Alert(Alert.AlertType.NONE).apply {
                                headerText = null
                                dialogPane.buttonTypes.add(ButtonType.CANCEL)
                            }.show()
                        }.preSize(500,60),
                        Button("警告弹窗").clickBN {
                            Alert(Alert.AlertType.WARNING).apply {
                                headerText = null
                            }.show()
                        }.preSize(500,60),
                        Button("选择弹窗").clickBN {
                            ChoiceDialog<String>("颜色", newFxList<String>().apply { addAll("红","橙","黄") })
                            .apply {
                                headerText = null
                                selectedItemProperty().addListener { observable, oldValue, newValue ->
                                    newValue.pln()
                                }
                            }.show()
                        }.preSize(500,60),
                        Button("文本输入弹窗").clickBN {
                            TextInputDialog("这是默认值")
                                    .apply {
                                        headerText = null
//                                        graphic = null
                                        (dialogPane.lookupButton(ButtonType.OK) as Button).clickBN {
                                            editor.text.pln()
                                        }
                                    }.show()
                        }.preSize(500,60)
                ).preSize(500,500)
        )
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(AlertDemo::class.java)
        }
    }

}