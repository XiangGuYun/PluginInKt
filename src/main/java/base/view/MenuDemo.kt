package base.view

import base.constant.AppTitle
import base.constant.Window
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.RadioMenuItem
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
                        "" to null,
                        "关闭-d" to EventHandler {
                            alert("关闭了")
                        }),
                listOf(
                        "剪切-c" to EventHandler {
                            alert("剪切")
                        },
                        "复制-c" to EventHandler {
                            alert("复制")
                        },
                        "粘贴-c" to EventHandler {
                            alert("粘贴")
                        },
                        "删除" to EventHandler {
                            alert("删除")
                        },
                        "2${RS}状态1-s-d${RS}状态2${RS}状态3${RS}状态4" to EventHandler {
                            val menu = it.source as RadioMenuItem
                            menu.isSelected = true
                            when(menu.text){
                                "状态1"->{
                                    alert("状态1")
                                }
                                "状态2"->{
                                    alert("状态2")
                                }
                                "状态3"->{
                                    alert("状态3")
                                }
                                "状态4"->{
                                    alert("状态4")
                                }
                            }
                        }
                        ),
                listOf(
                        "TIP" to EventHandler {
                            alert("TIP")
                        },
                        "更多,更多1-c,更多2-c-s,更多3-c-s-d" to EventHandler {
                            when((it.source as MenuItem).text){
                                "更多1"->{
                                    alert("更多1")
                                }
                                "更多2"->{
                                    alert("更多2")
                                }
                                "更多3"->{
                                    alert("更多3")
                                }
                            }
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
                    "更多"->{
                        (it as Menu).items.find {
                            it.text == "更多1"
                        }?.graphic = ImageView(Image("image/sanguo.png"))
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