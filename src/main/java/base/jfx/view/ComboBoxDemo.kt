package base.jfx.view

import base.constant.AppTitle
import base.constant.TextView
import base.constant.Window
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.layout.VBox
import sample.base.BaseApp

@AppTitle("ComboBoxDemo")
class ComboBoxDemo : BaseApp() {

    override fun init(window: Window) {
        val cb = ComboBox<String>()
        cb.items.addAll("Item1", "Item2", "Item3", "Item4", "Item5", "Item6")
        cb.isEditable = true
        cb.promptText = "请输入选择"
        cb.focus(false)
//        cb.placeholder = TextView("暂无选项") //设置占位图
        cb.visibleRowCount = 5 //最多可视多少下拉项
        //选项过滤
        val allItems = cb.items
        cb.editorProperty().get().textProperty().addListener { observable, oldValue, newValue ->
            val filterList = allItems.filtered {
                it.contains(newValue)
            }
            if (filterList.size != allItems.size)
                cb.items = filterList
            else
                cb.placeholder = TextView("暂无选项").preSize(200,500).align(Pos.CENTER)
        }

        window.scene = Scene(VBox().addChildren(cb, Button("添加").clickBtn {
            cb.items.add(0, "NewItem")
        }).preSize(200, 500))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ComboBoxDemo::class.java)
        }
    }

}