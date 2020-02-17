package base.jfx.view

import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeType
import javafx.scene.text.*
import sample.base.BaseApp

@AppTitle("TextFontDemo")
class TextFontDemo : BaseApp() {

    override fun init(window: Window) {
        val ttfPath = javaClass.classLoader.getResource("other/BoxOutline.ttf").toExternalForm()
        Font.getFamilies().forEach {
            it.pln()
        }
        "========================".pln()
        Font.getFontNames().forEach {
            it.pln()
        }
        window.scene = Scene(VBox().addChildren(
                Text("Hello JavaFX").apply {
                    font = Font.font("Courier", 25.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                },
                Text("Hello JavaFX").apply {
                    font = Font.font("汉仪雅酷黑W", 25.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                },
                Text("Hello JavaFX").apply {
                    font = Font.loadFont(ttfPath, 40.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                },
                Text("Hello JavaFX").apply {
                    font = Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 25.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                },
                Text("Hello JavaFX").apply {
                    font = Font.font("汉仪趣报W", FontWeight.BOLD, FontPosture.ITALIC, 50.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                    fill = Paint.valueOf("#ff6666")//字体颜色
                    stroke = Paint.valueOf("#ffff66")//描边颜色
                    strokeWidth = 2.0
                    strokeType = StrokeType.CENTERED //描边类型
                    isSmooth = true //抗锯齿
                    isUnderline = true
                    isStrikethrough = true
                    fontSmoothingType = FontSmoothingType.LCD//字体平滑
                },
                Text("静夜思\n床前明月光\n疑是地上霜\n举头望明月\n低头思故乡").apply {
                    font = Font.font("汉仪青云W", 25.0)
                    wrappingWidth = 300.0
                    textAlignment = TextAlignment.CENTER
                    lineSpacing = 10.0 //行间距
                    textAlignment = TextAlignment.CENTER
                }

        ).preSize(300, 600).apply {
            padding = Insets(20.0)
            bgColor("#5555ff")
        }
        )
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TextFontDemo::class.java)
        }
    }

}