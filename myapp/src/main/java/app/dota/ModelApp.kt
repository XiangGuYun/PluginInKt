package app.dota

import base.constant.AppTitle
import base.constant.Window
import base.utils.DmUtils
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import sample.base.BaseApp
import java.io.File

@AppTitle("")
class ModelApp : BaseApp(), DmUtils{

    override fun init(window: Window) {
        val dm = initDmCom()
        dm.reg()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ModelApp::class.java)
        }
    }

}