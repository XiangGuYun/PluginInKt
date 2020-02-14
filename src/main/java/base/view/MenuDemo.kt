package base.view

import base.constant.AppTitle
import base.constant.Window
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import sample.base.BaseApp

/**
 * MenuBar->Menu->MenuItem
 */
@AppTitle("MenuDemo")
class MenuDemo : BaseApp() {

    override fun init(window: Window) {

        val menuBar = createMenu(listOf("文件", "编辑", "帮助"), listOf(
                listOf(
                        "打开  CTRL+O" to EventHandler {
                            alert("打开")
                        },
                        "保存" to EventHandler {
                            alert("保存了")
                        },
                        "另存为" to EventHandler {
                            alert("另存了")
                        },
                        "关闭" to EventHandler {
                            alert("关闭了")
                        }),
                listOf(
                        "剪切" to EventHandler {
                            alert("剪切")
                        },
                        "复制" to EventHandler {
                            alert("复制")
                        },
                        "粘贴" to EventHandler {
                            alert("粘贴")
                        },
                        "删除" to EventHandler {
                            alert("删除")
                        }),
                listOf(
                        "TIP" to EventHandler {
                            alert("TIP")
                        },
                        "更多" to EventHandler {
                            alert("更多")
                        }
                )
        ))

        window.scene = Scene(AnchorPane(menuBar.first.marginAp(0, 0, 0, -1)).preSize(500, 500))

        menuBar.second.forEach {
            it.forEach {
                when(it.text){
                    "打开  CTRL+O"-> {
                        //设置快捷键
                        window.scene.setHotKey("ctrl+o"){
                            alert("用快捷键打开了")
                        }
                        //设置图标
                        it.graphic = ImageView(Image("image/sanguo.png"))
                    }
                }
            }
        }


    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MenuDemo::class.java)
        }
    }

}