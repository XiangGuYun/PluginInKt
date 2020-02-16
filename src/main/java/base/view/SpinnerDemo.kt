package base.view

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.Spinner
import javafx.scene.control.SpinnerValueFactory
import javafx.scene.layout.VBox
import sample.base.BaseApp

@AppTitle("")
class SpinnerDemo : BaseApp(){

    data class Student(val name:String, val age:Int)

    override fun init(window: Window) {

        window.scene = Scene(VBox().addChildren(
                Spinner<Int>(0,10,5).preSize(300,30),
                Spinner<Double>(0.0,1.0,0.1, 0.1).preSize(300,30),
                Spinner<String>(newFxList<String>().apply {
                    addAll(('A'..'Z').toList().map { "item$it" })
                }).preSize(300,30).apply {
                    valueProperty().addListener { observable, oldValue, newValue ->
                        newValue.pln()
                    }
                },
                Spinner<Student>(newFxList<Student>().apply {
                    addAll(('A'..'Z').toList().mapIndexed { index, c ->
                        Student("student$c", index)
                    })
                }).preSize(300,30).apply {
                    editor.text = "studentA"
                    valueProperty().addListener { observable, oldValue, newValue ->
                        editor.text = (newValue as Student).name
                        newValue.pln()
                    }
                }
        ))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SpinnerDemo::class.java)
        }
    }

}