package base.view

import base.constant.AppTitle
import base.constant.RadioGroup
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.CheckBox
import javafx.scene.control.RadioButton
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import sample.base.BaseApp

/**
 * 单选按钮和多选按钮
 */
@AppTitle("RadioButtonDemo")
class RadioButtonDemo : BaseApp() {

    override fun init(window: Window) {
        val rbList = (1..5).toList().mapIndexed { index, i ->
            RadioButton(" rb${i} ")
        }
        val rg = RadioGroup()
        rg.toggles.addAll(rbList)
        //监听选中事件
        rg.selectedToggleProperty().addListener { _, _, newValue ->
            newValue.pln()
        }
        rbList[1].isSelected = true

        val cbList = (1..5).toList().mapIndexed { index, i ->
            CheckBox(" cb${i} ").apply{
                selectedProperty().addListener { _, _, newValue ->
                    newValue.pln() //是否选中
                }
            }
        }
        cbList[0].isSelected = true
        cbList[1].isIndeterminate = true //是否不确定

        window.height = 200.0
        window.scene = Scene(VBox().addChildren(
                HBox().addChildren(rbList).marginVb(20,20,20,0),
                HBox().addChildren(cbList).marginVb(20,20,20,0)
        ))

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(RadioButtonDemo::class.java)
        }
    }

}