package base.jfx.view

import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.Stage
import sample.base.BaseApp
import java.io.File

/**
 * 文件和文件夹选择器
 */
@AppTitle("FileSelectApp")
class FileSelectApp : BaseApp() {

    override fun init(window: Window) {
        window.scene = Scene(VBox().addChildren(
                Button("单选文件窗口").clickBN {
                    val fileSelected = FileChooser().apply {
                        //设置初始目录
                        initialDirectory = File("D:")
                        //设置文件类型过滤
                        extensionFilters.addAll(
                                FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png"),
                                FileChooser.ExtensionFilter("文本类型", "*.txt", "*.md"),
                                FileChooser.ExtensionFilter("所有类型", "*.*")
                        )
                    }.showOpenDialog(Stage())
                    fileSelected?.absolutePath?.pln("文件的绝对路径是：")
                },
                Button("多选文件窗口").clickBN {
                    val fileSelectedList = FileChooser().apply {
                        //设置初始目录
                        initialDirectory = File("D:")
                        //设置文件类型过滤
                        extensionFilters.addAll(
                                FileChooser.ExtensionFilter("图片类型", "*.jpg", "*.png"),
                                FileChooser.ExtensionFilter("文本类型", "*.txt", "*.md"),
                                FileChooser.ExtensionFilter("所有类型", "*.*")
                        )
                    }.showOpenMultipleDialog(Stage())
                    fileSelectedList?.forEach {
                        it?.absolutePath?.pln("文件的绝对路径是：")
                    }
                },
                Button("打开文本").clickBN {
                    val fileSelected = FileChooser().apply {
                        //设置初始目录
                        initialDirectory = File("D:")
                        //设置文件类型过滤
                        extensionFilters.addAll(
                                FileChooser.ExtensionFilter("文本类型", "*.txt", "*.md", "*.imi")
                        )
                    }.showOpenDialog(Stage())
                    fileSelected?.readText()?.pln("读取到的文本内容是： ")
                },
                Button("保存文本").clickBN {
                    val fileSelected = FileChooser().apply {
                        title = "保存文件"
                        //初始保存的文本文件名称
                        initialFileName = "保存的文本"
                        //设置初始目录
                        initialDirectory = File("D:")
                        //设置文件类型过滤
                        extensionFilters.addAll(
                                FileChooser.ExtensionFilter("文本类型", "*.txt", "*.md", "*.imi")
                        )
                    }.showOpenDialog(Stage())
                    fileSelected?.appendText("\n你好呀")
                },
                Button("目录选择器").clickBN {
                    val dir = DirectoryChooser().apply {
                        title = "文件夹选择器"
                        initialDirectory = File("D:")
                    }.showDialog(Stage())
                    dir?.absolutePath?.pln("选择的文件夹路径是：")
                }
        ).preSize(200, 300).apply {
            alignment = Pos.CENTER
            spacing = 10.0
        })
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(FileSelectApp::class.java)
        }
    }

}