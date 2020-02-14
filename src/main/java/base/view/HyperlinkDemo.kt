package base.view

import base.constant.AppTitle
import base.constant.Window
import javafx.application.HostServices
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Hyperlink
import sample.base.BaseApp

@AppTitle("HyperlinkDemo")
class HyperlinkDemo : BaseApp(){

    override fun init(window: Window) {
        val link = Hyperlink("www.baidu.com", Button("跳转")).preSize(300,200).align(Pos.CENTER).textSize(20)
                .clickBtn {
                    hostServices.showDocument("www.baidu.com")
                }
        window.scene = Scene(link)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(HyperlinkDemo::class.java)
        }
    }

}