package app.dnf.dh

import app.dnf.DnfUtils
import com.jacob.com.Dispatch
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

interface Tab2Presenter : DnfUtils {

    fun tab2(dm: Dispatch): Node {
        val tfPath = TextField("C:\\Users\\Administrator\\Desktop\\yellow_dragon")
        val tfPicName = TextField()
        val tfPicRect = TextField()
        val tfColorShift = TextField("101010")
        val tfSim = TextField("0.9")
        return VBox().addChildren(
                HBox().addChildren(
                        Label("设置路径").marginHb(0, 4, 0, 0),
                        tfPath,
                        Button("确定").clickBN {
                            alert("设置是否成功？${dm.setPath(tfPath.text)}")
                        }
                ).apply {
                    spacing = 10.0
                },
                HBox().addChildren(
                        Label("图片名称").marginHb(0, 4, 0, 0),
                        tfPicName
                ).apply {
                    spacing = 10.0
                },
                HBox().addChildren(
                        Label("图片矩形").marginHb(0, 4, 0, 0),
                        tfPicRect,
                        Button("+5").clickBN {
                            if(tfPicRect.text.isEmpty() || tfPicRect.text.split(",").size != 4)
                                return@clickBN
                            val rect = tfPicRect.text.split(",")
                            tfPicRect.text = "${(rect[0].toInt() - 5)},${rect[1].toInt() - 5},${rect[2].toInt() + 5},${rect[3].toInt() + 5}"
                        },
                        Button("-5").clickBN {
                            if(tfPicRect.text.isEmpty() || tfPicRect.text.split(",").size != 4)
                                return@clickBN
                            val rect = tfPicRect.text.split(",")
                            tfPicRect.text = "${(rect[0].toInt() + 5)},${rect[1].toInt() + 5},${rect[2].toInt() - 5},${rect[3].toInt() - 5}"
                        }
                ).apply {
                    spacing = 10.0
                },
                HBox().addChildren(
                        Label("颜色偏移").marginHb(0, 4, 0, 0),
                        tfColorShift
                ).apply {
                    spacing = 10.0
                },
                HBox().addChildren(
                        Label("相似度   ").marginHb(0, 4, 0, 0),
                        tfSim
                ).apply {
                    spacing = 10.0
                },
                Button("查找").preSize(100,30).clickBN {
                    if(tfPicRect.text.isEmpty() || tfPicRect.text.split(",").size != 4)
                        return@clickBN
                    val rect = tfPicRect.text.split(",")
                    val result = dm.findPic(rect[0].toInt(),rect[1].toInt(),rect[2].toInt(),rect[3].toInt(),tfPicName.text, tfColorShift.text, tfSim.text.toDouble())
                    val dialog = alert("查找结果：$result")
                    (dialog.dialogPane.lookupButton(ButtonType.OK) as Button).apply {
                        prefWidth = 100.0
                        text = "确定并复制代码"
                        dm.setClipboard("dm.findPic(${rect[0].toInt()},${rect[1].toInt()},${rect[2].toInt()},${rect[3].toInt()},\"${tfPicName.text}\", \"${tfColorShift.text}\", ${tfSim.text.toDouble()})")
                    }
                }
        ).apply {
            padding = Insets(10.0)
            spacing = 10.0
            prefHeight = 300.0
            alignment = Pos.CENTER
        }
    }

}