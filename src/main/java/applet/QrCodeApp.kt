package applet

import base.constant.AppTitle
import base.constant.LayoutId
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import sample.base.BaseApp
import java.io.File

@LayoutId("qrcode")
@AppTitle("二维码扫描")
class QrCodeApp : BaseApp(){

    override fun init(window: Window) {

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(QrCodeApp::class.java)
        }
    }

}