package base.jfx.view

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.ChoiceBox
import javafx.scene.layout.HBox
import sample.base.BaseApp

@AppTitle("ChoiceBoxDemo")
class ChoiceBoxDemo : BaseApp(){

    override fun init(window: Window) {
        val cb = ChoiceBox<String>()
        cb.items.addAll((1..10).toList().mapIndexed { index, i ->
            "Item$i"
        })
        cb.preSize(100,30)
        cb.value = cb.items[1]

        val cb1 = ChoiceBox<String>()
        val list1 = newFxList<String>()
        list1.addAll((1..10).toList().mapIndexed { index, i -> "颜色$i" })
        val list2 = newFxList<String>()
        list2.addAll((1..10).toList().mapIndexed { index, i -> "菜单$i" })
        cb1.items = list1

        cb.selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
            if(newValue.contains("1")){
                cb1.items = list1
                cb1.value = "颜色1"
            }
            else{
                cb1.items = list2
                cb1.value = "菜单1"
            }
            newValue.pln()
        }
        cb1.value = "颜色1"

        window.scene = Scene(HBox().addChildren(cb, cb1).preSize(200,500))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ChoiceBoxDemo::class.java)
        }
    }

}