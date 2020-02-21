package base.jfx.view

import base.constant.AppTitle
import base.constant.IV
import base.constant.LB
import base.constant.Window
import base.utils.CommonUtils
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import sample.base.BaseApp

@AppTitle("ListViewDemo")
class ListViewDemo : BaseApp(){

    override fun init(window: Window) {
        val list = newFxList<News>()
        list.addAll((1..100).toList().mapIndexed { index, i ->
            News("pvz.jpg", "植物大战僵尸$i", "$content$i")
        })
        val lv = ListView<News>().preSize(800,800)
        lv.items = list
        lv.placeholder = Label("没有数据").textSize(30)
//        lv.items.add(0, "新Item")
        lv.orientation = Orientation.VERTICAL
//        lv.selectionModel.selectionMode = SelectionMode.MULTIPLE
//        lv.selectionModel.selectedItems.addListener(ListChangeListener<String> {
//            alert(it.addedSize)
//        })
        lv.setCellFactory {
            MyListCell()
        }
        window.scene = Scene(lv)
    }

    data class News(val img:String="", val title:String="", val content: String="")

    val content = "《植物大战僵尸》是由PopCap Games开发的一款益智策略类单机游戏，于2009年5月5日发售。玩家通过武装多种植物切换不同的功能，快速有效地把僵尸阻挡在入侵的道路上。不同的敌人，不同的玩法构成五种不同的游戏模式，加之黑夜、浓雾以及泳池之类的障碍增加了游戏挑战性。"

    class MyListCell(): ListCell<News>(), CommonUtils {
        override fun updateItem(item: News?, empty: Boolean) {
            if(!empty){
                this.graphic = HBox().addChildren(
                        IV("image/${item!!.img}").fitSize(300,200),
                        VBox().addChildren(
                                LB(item.title).textSize(30).marginVb(20,0,0,0),
                                LB(item.content).textSize(16.5).apply {
                                    isWrapText = true
                                    maxWidth = 800.0-40-300-50
                                }.marginVb(20,20,0,0)
                        )
                ).apply {
                    padding = Insets(20.0)
                    bgColor(randomColor())
                    setOnMousePressed {
                        this.alpha(0.5)
                    }
                    setOnMouseReleased {
                        this.alpha(0.0)
                    }
                    setOnMouseEntered {
                        this.alpha(0.1)
                    }
                    setOnMouseExited {
                        this.alpha(0.0)
                    }
                }
                this.maxHeight = 340.0
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ListViewDemo::class.java)
        }
    }

}