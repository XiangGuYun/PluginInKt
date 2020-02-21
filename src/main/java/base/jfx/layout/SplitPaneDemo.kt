package base.jfx.layout

import base.constant.AppTitle
import base.constant.LB
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import sample.base.BaseApp

/**
 * 拆分面板，可自由拖拽
 */
@AppTitle("SplitPaneDemo")
class SplitPaneDemo : BaseApp(){

    override fun init(window: Window) {
        val sp = SplitPane().preSize(600,600)
        sp.items.addAll((1..3).toList().mapIndexed { index, i ->
            //关键1：设置每个面板的初始宽度
            LB().preSize(400,600).bgColor(randomColor()).apply {
                //关键2：设置每个面板的最小宽度
                minWidth = when(index){
                    0->100.0
                    1->200.0
                    else->100.0
                }
            }
        })
        window.scene = Scene(sp)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SplitPaneDemo::class.java)
        }
    }

}