package base.jfx.view

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import sample.base.BaseApp

/**
 * Menu的三个子类
 * ButtonMenu
 * SplitButtonMenu
 * ContextMenu
 */
@AppTitle("MenuButtonDemo")
class MenuButtonDemo : BaseApp(){

    override fun init(window: Window) {

        val cm = ContextMenu().apply { items.addAll((1..10).toList().map { MenuItem("item$it") }) }

        window.scene = Scene(VBox().addChildren(
                MenuButton().apply {
                    prefWidth = 200.0
                    items.addAll((1..10).toList().map { MenuItem("item$it") })
                },
                SplitMenuButton().apply {
                    prefWidth = 200.0
                    items.addAll((1..10).toList().map { MenuItem("item$it") })
                },
                Button("上下文菜单").apply {
                    contextMenu = cm
                    setOnContextMenuRequested {
                        "打开了上下文菜单".pln()
                    }
                }

        ).preSize(200,500))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MenuButtonDemo::class.java)
        }
    }

}