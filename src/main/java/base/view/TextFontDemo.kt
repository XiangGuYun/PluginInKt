package base.view

import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import sample.base.BaseApp

@AppTitle("TextFontDemo")
class TextFontDemo : BaseApp(){

    override fun init(window: Window) {
        window.scene = Scene(VBox().addChildren(
                Text("Hello JavaFX").apply {
                    font = Font.font("Courier",25.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                },
                Text("Hello JavaFX").apply {
                    font = Font.font("Impact",25.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                }
        ).preSize(300,600).apply { padding = Insets(20.0) })
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TextFontDemo::class.java)
        }
    }

}