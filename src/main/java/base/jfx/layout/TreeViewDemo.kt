package base.jfx.layout

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.VBox
import sample.base.BaseApp
import java.io.File

/**
 * 树状图
 */
@AppTitle("TreeViewDemo")
class TreeViewDemo : BaseApp(){

    override fun init(window: Window) {
        val treeView = TreeView<String>()

        val treeItem = TreeItem<String>("中国")

        val treeItem1 = TreeItem<String>("黑龙江")
        val treeItem1_1 = TreeItem<String>("哈尔滨")
        val treeItem1_2 = TreeItem<String>("佳木斯")
        val treeItem1_3 = TreeItem<String>("大庆")

        val treeItem2 = TreeItem<String>("广东")
        val treeItem2_1 = TreeItem<String>("广州")
        val treeItem2_2 = TreeItem<String>("深圳")
        val treeItem2_3 = TreeItem<String>("东莞")

        val treeItem3 = TreeItem<String>("浙江")
        val treeItem3_1 = TreeItem<String>("杭州")
        val treeItem3_2 = TreeItem<String>("宁波")
        val treeItem3_3 = TreeItem<String>("温州")

        treeView.root = treeItem
        treeItem.children.addAll(treeItem1, treeItem2, treeItem3)
        treeItem1.children.add(treeItem1_1)
        treeItem1.children.add(treeItem1_2)
        treeItem1.children.add(treeItem1_3)
        treeItem2.children.add(treeItem2_1)
        treeItem2.children.add(treeItem2_2)
        treeItem2.children.add(treeItem2_3)
        treeItem3.children.add(treeItem3_1)
        treeItem3.children.add(treeItem3_2)
        treeItem3.children.add(treeItem3_3)

        treeView.fixedCellSize = 20.0

        window.scene = Scene(VBox().addChildren(treeView))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TreeViewDemo::class.java)
        }
    }

}