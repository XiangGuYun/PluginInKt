package sample.base

import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Labeled
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.text.Font

interface ViewHelper {

    fun <T:Labeled> T.click(function: (MouseEvent)->Unit){
        setOnMouseClicked{
            function.invoke(it)
        }
    }

    fun <T:Node> T.click(function: (MouseEvent)->Unit){
        setOnMouseClicked{
            function.invoke(it)
        }
    }

    fun Label.setTextColor(color: Color){
        textFill = color
    }

    fun Label.setTextSize(size: Number){
        font = Font.font(size.toDouble())
    }

}