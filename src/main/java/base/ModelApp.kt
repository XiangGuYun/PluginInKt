package base

import base.constant.AppTitle
import base.constant.Window
import sample.base.BaseApp

@AppTitle("")
class ModelApp : BaseApp(){

    override fun init(window: Window) {

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ModelApp::class.java)
        }
    }

}