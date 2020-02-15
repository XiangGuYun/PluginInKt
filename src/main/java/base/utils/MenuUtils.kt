package base.utils

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination


interface MenuUtils {

    val RS get() = "-r-"//单选菜单项的分隔符

    /**
     * 创建菜单, 支持单选菜单，多选菜单，二级菜单（使用,分隔）
     * 菜单项名中带有-s：表示选中状态
     * 菜单项中带有-c：表示属于多选菜单项
     * 菜单项中带有-d：表示不可用
     */
    fun createMenu(menuList: List<String>, menuItemLIst: List<List<Pair<String, EventHandler<ActionEvent>?>>>): Pair<MenuBar, List<List<MenuItem>>> {
        val itemList = menuItemLIst.map {
            val pairList = it.filter { !it.first.contains(RS) }
            pairList.map { pair ->
                when {
                    pair.first == "" -> {
                        SeparatorMenuItem()
                    }
                    pair.first.contains(",") -> {
                        val list = pair.first.split(",")
                        println(list.size)
                        Menu(list[0]).apply {
                            list.forEachIndexed { i, it ->
                                if (i != 0) {
                                    items.add(
                                            if (!it.contains("-c")) {
                                                MenuItem(it.replace("-d","")).apply {
                                                    onAction = pair.second
                                                    isDisable = it.contains("-d")
                                                }
                                            } else {
                                                if (it.contains("-s")) {
                                                    CheckMenuItem(it.replace("-c", "").replace("-s", "").replace("-d","")).apply {
                                                        onAction = pair.second
                                                        isSelected = true
                                                        isDisable = it.contains("-d")
                                                    }
                                                } else {
                                                    CheckMenuItem(it.replace("-c", "").replace("-d", "")).apply {
                                                        onAction = pair.second
                                                        isDisable = it.contains("-d")
                                                    }
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                    pair.first.contains("-c") -> {
                        if (pair.first.contains("-s")) {
                            CheckMenuItem(pair.first.replace("-c", "").replace("-s", "").replace("-d","")).apply {
                                onAction = pair.second
                                isSelected = true
                                isDisable = pair.first.contains("-d")
                            }
                        } else {
                            CheckMenuItem(pair.first.replace("-c", "").replace("-d","")).apply {
                                onAction = pair.second
                                isDisable = pair.first.contains("-d")
                            }
                        }
                    }
                    else -> {
                        MenuItem(pair.first.replace("-d","")).apply {
                            onAction = pair.second
                            isDisable = pair.first.contains("-d")
                        }
                    }
                }
            }.toMutableList()
        }.toMutableList()

        menuItemLIst.forEachIndexed { i, it ->
            it.forEach { pair ->
                if (pair.first.contains(RS)) {
                    val list = pair.first.split(RS)
                    val tg = ToggleGroup()
                    val menus = list.subList(1, list.size - 1).map {
                        if (it.contains("-s")) {
                            RadioMenuItem(it.replace("-s", "").replace("-d","")).apply {
                                toggleGroup = tg
                                onAction = pair.second
                                isSelected = true
                                isDisable = it.contains("-d")
                            }
                        } else {
                            RadioMenuItem(it.replace("-d","")).apply {
                                toggleGroup = tg
                                onAction = pair.second
                                isDisable = it.contains("-d")
                            }
                        }
                    }
                    itemList[i].addAll(list[0].toInt(), menus)
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