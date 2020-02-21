package base

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import sample.base.BaseApp
import java.io.File

@AppTitle("")
class ModelApp : BaseApp(){

    override fun init(wd: Window) {
        alert("这个世界")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ModelApp::class.java)
        }
    }

}