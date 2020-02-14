package base.utils

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination


interface MenuUtils {

    /**
     * 创建菜单
     */
    fun createMenu(menuList:List<String>, menuItemLIst: List<List<Pair<String, EventHandler<ActionEvent>>>>): Pair<MenuBar, List<List<MenuItem>>> {
        val itemList = menuItemLIst.map {
            it.map {
                MenuItem(it.first).apply {
                    onAction = it.second
                }
            }
        }
        return MenuBar().apply {
            menuList.forEachIndexed { index, s ->
                menus.add(Menu(s).apply {
                    items.addAll(itemList[index])
                })
            }
        } to itemList
    }

}