package base.jfx.view

import base.constant.AppTitle
import base.constant.Btn
import base.constant.FXIV
import base.constant.Window
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Pagination
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.util.Callback
import sample.base.BaseApp

/**
 * 类似于Android中的ViewPager+Indicator
 */
@AppTitle("PaginationDemo")
class PaginationDemo : BaseApp(){

    override fun init(window: Window) {
        window.scene = Scene(AnchorPane().addChildren(
                Pagination().apply {
                    pageCount = 10 //设置页数 Pagination.INDETERMINATE表示不确定
                    maxPageIndicatorCount = 5 //设置最多同时显示多少页
                    currentPageIndex = 3 //设置选中第几项，从0开始
                    styleClass.add(Pagination.STYLE_CLASS_BULLET) //子弹样式
                    //监听翻页
                    currentPageIndexProperty().addListener { observable, oldValue, newValue ->
                        newValue.pln()
                    }
                    //设置页面内容
                    pageFactory = Callback<Int, Node> {
                        VBox().addChildren(
                                FXIV("image/pvz.jpg"),
                                Btn("Button$it")
                        ).apply {
                            alignment = Pos.CENTER
                            spacing = 10.0
                        }
                    }
                    marginAp(0,20,0,20)
                }
        ).preSize(500, 500))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PaginationDemo::class.java)
        }
    }

}