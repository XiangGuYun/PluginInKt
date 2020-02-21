package base.jfx.view

import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollBar
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import sample.base.BaseApp
import java.util.*

@AppTitle("ScrollBarDemo")
class ScrollBarDemo : BaseApp(){

    override fun init(window: Window) {
        val sb = ScrollBar().preSize(5,500)
        sb.orientation = Orientation.VERTICAL
        sb.valueProperty().addListener { observable, oldValue, newValue ->
            newValue.pln() // 0~100
        }
        sb.visibleAmount = 30.0 //设置滚动条的长度
        sb.value = 20.0
        sb.max = 100.0
        sb.min = 0.0
        sb.unitIncrement = 1.0//每次按方向按钮时滚动的距离
        sb.blockIncrement = 100.0//设置点击变化后的最大值
        window.scene = Scene(HBox().addChildren(
                VBox().preSize(495,500),
                sb,
                Button("滚动").clickBN {
                    Timer().schedule(object :TimerTask(){
                        override fun run() {
                            sb.increment()
                            if(sb.value == 100.0){
                                "结束了".pln()
                                cancel()
                            }
                        }
                    },0,100)
                }
        ).preSize(500,500))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ScrollBarDemo::class.java)
        }
    }

}