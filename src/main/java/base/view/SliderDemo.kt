package base.view

import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.Slider
import javafx.scene.layout.VBox
import sample.base.BaseApp
import java.text.DecimalFormat
import java.util.*

/**
 * 类似于Android中的ProgressBar和SeekBar
 */
@AppTitle("SliderDemo")
class SliderDemo : BaseApp(){

    override fun init(window: Window) {

        val pb = ProgressBar(0.0).marginVb(10,20,10,0).apply {
            preSize(400,10)
        }

        val pi = ProgressIndicator(0.0).preSize(100,100)

        window.scene = Scene(VBox().addChildren(
                Slider(0.0,100.0,50.0).marginVb(10,20,10,0).apply {
                    isShowTickMarks = true //显示刻度
                    isShowTickLabels = true //显示数值
                    majorTickUnit = 10.0 //显示刻度单元
                    value = 20.0 //设置当前值
                    orientation = Orientation.HORIZONTAL
                    valueChangingProperty().addListener { observable, oldValue, newValue ->
                        newValue.pln()
                    }
                    valueProperty().addListener { observable, oldValue, newValue ->
                        newValue.pln()
                    }
                },
                ProgressBar(0.5).marginVb(10,20,10,0).apply {
                    progress = 0.9 //设置当前值
                    preSize(400,10)
                },
                pb,
                ProgressIndicator().preSize(100,100),
                pi
        ).preSize(500,500))

        var progress = 0.0
        Timer().schedule(object :TimerTask(){
            override fun run() {
                runOnMainThread {
                    progress += 0.001
                    progress = String.format("%.3f", progress).toDouble()
                    if(progress<=1.0){
                        pb.progress = progress
                        pi.progress = progress
                        progress.pln()
                    } else {
                        cancel()
                    }
                }
            }
        },1000,10)

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SliderDemo::class.java)
        }
    }

}