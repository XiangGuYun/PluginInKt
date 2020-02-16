package base.view

import base.constant.*
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import sample.base.BaseApp

@AppTitle("LabelDemo")
class LabelDemo : BaseApp(){

    val content = "《植物大战僵尸》是由PopCap Games开发的一款益智策略类单机游戏，于2009年5月5日发售。玩家通过武装多种植物切换不同的功能，快速有效地把僵尸阻挡在入侵的道路上。不同的敌人，不同的玩法构成五种不同的游戏模式，加之黑夜、浓雾以及泳池之类的障碍增加了游戏挑战性。"

    override fun init(window: Window) {

        val tv1 = TextView("tv1", Btn("按钮1")).align(Pos.CENTER)
        tv1.contentDisplay = ContentDisplay.RIGHT
        val tv2 = TextView("", HBox().addChildren(
                FXIV("image/pvz.jpg").fitSize(300,200),
                VBox().addChildren(
                        TextView("植物大战僵尸").textSize(30).marginVb(20,0,0,0),
                        TextView(content).textSize(16.5).apply {
                            isWrapText = true
                            maxWidth = 800.0-40-300
                        }.marginVb(20,20,0,0)
                )
        ).apply {
            padding = Insets(20.0)
            bgColor("#66ff66")
        })
        tv2.maxHeight = 340.0
//        tv1.contentDisplay = ContentDisplay.RIGHT

        window.scene = Scene(VBox().addChildren(tv1, tv2).preSize(800,800))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(LabelDemo::class.java)
        }
    }

}