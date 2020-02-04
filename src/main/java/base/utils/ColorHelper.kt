package sample.base

import javafx.scene.paint.Color

interface ColorHelper {

    fun color(red:Int,green:Int,blue:Int,opacity:Double=1.0): Color {
        return Color.rgb(red,green,blue,opacity)
    }

    fun color(value:String,alpha:Double=1.0): Color {
        return Color.web(value,alpha)
    }

}