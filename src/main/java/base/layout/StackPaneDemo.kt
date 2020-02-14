package base.layout

import base.constant.Resizable
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import sample.base.BaseApp

@Resizable(false)
class StackPaneDemo : BaseApp() {
    override fun init(window: Window) {
        window.size(720, 480)
        val sp = StackPane()
        sp.addChild(AnchorPane().bgColor(randomColor()).marginSp(10,10,10,10))
        sp.addChild(AnchorPane().bgColor(randomColor()).marginSp(30,30,30,30))
        sp.addChild(AnchorPane().bgColor(randomColor()).marginSp(50,50,50,50))
        sp.addChild(AnchorPane().bgColor(randomColor()).marginSp(70,70,70,70))
        sp.addChild(AnchorPane().bgColor(randomColor()).marginSp(90,90,90,90))
        sp.addChild(AnchorPane().bgColor(randomColor()).marginSp(110,110,110,110))
        window.scene = Scene(sp)
    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(StackPaneDemo::class.java)
        }
    }
}