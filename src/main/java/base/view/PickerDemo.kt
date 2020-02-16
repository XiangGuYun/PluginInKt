package base.view

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.ColorPicker
import javafx.scene.control.DatePicker
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import sample.base.BaseApp
import java.time.LocalDate

@AppTitle("")
class PickerDemo : BaseApp(){

    override fun init(window: Window) {
        val vbox = VBox().preSize(500,500)
        val cp = ColorPicker()
        cp.valueProperty().addListener { observable, oldValue, newValue ->
            newValue.pln()
            vbox.bgColor("#"+newValue.toString().substring(2))
        }

        val dp = DatePicker(LocalDate.now()).apply {
            isEditable = false
            valueProperty().addListener { observable, oldValue, newValue ->
                (newValue.year.toString()+"年"+newValue.monthValue+"月"+newValue.dayOfMonth+"日").pln()
                newValue.pln()
            }
        }

        window.scene = Scene(vbox.addChildren(cp, dp.marginVb(0,50,0,0)))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PickerDemo::class.java)
        }
    }

}