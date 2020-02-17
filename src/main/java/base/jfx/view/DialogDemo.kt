package base.jfx.view

import base.constant.AppIcon
import base.constant.AppTitle
import base.constant.Window
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import sample.base.BaseApp

/**
 * Dialog本质是对Window的一种定制化，类似于Android的AlertDialog
 */
@AppIcon("sanguo.png")
@AppTitle("DialogDemo")
class DialogDemo : BaseApp(){

    override fun init(window: Window) {
        window.scene = Scene(
                VBox().addChildren(
                        Button("弹窗").clickBtn {
                            val dialog = Dialog<ButtonType>()
                            dialog.initStyle(StageStyle.UTILITY)
                            dialog.dialogPane.background = Background.EMPTY
                            dialog.dialogPane.buttonTypes.apply {
                                add(ButtonType.OK) //确定
                                add(ButtonType.APPLY) //应用

                                add(ButtonType.CANCEL)
                                add(ButtonType.FINISH)
                                add(ButtonType.CLOSE) //关闭

                                add(ButtonType.YES)
                                add(ButtonType.NO)

                                add(ButtonType.PREVIOUS)
                                add(ButtonType.NEXT)
                            }
                            (dialog.dialogPane.lookupButton(ButtonType.OK) as Button).clickBtn {
                                "OK".pln()
                            }
                            dialog.graphic = ImageView(Image("image/pvz.jpg",300.0,300.0,true,true))
                            dialog.contentText = "xxxxxxxxxxxxxxxxxxxxxxxx"
                            dialog.title = "标题"
//                            dialog.headerText = "headerText"
                            dialog.onCloseRequest = EventHandler<DialogEvent>{
                                "请求关闭了".pln()
                            }
                            dialog.showAndWait().ifPresent {
                                if(it.buttonData == ButtonBar.ButtonData.APPLY){
                                    dialog.contentText = "1111111"
                                }
                            }
                            dialog.show()
                            //不支持高度自定义
//                            Dialog<ButtonType>().apply {
//                                graphic = StackPane().addChildren(
//                                        ImageView(Image("image/pvz.jpg",300.0,200.0,true,true))
//                                )
//                                dialogPane.preSize(300,200)
//                            }
                        },
                        Button("弹窗").clickBtn {
                            val dialog = Dialog<ButtonType>()
                            dialog.title = "数学题"
                            dialog.initStyle(StageStyle.UTILITY)
                            dialog.dialogPane.buttonTypes.apply {
                                add(ButtonType.YES)
                                add(ButtonType.NO)
                            }
                            dialog.contentText = "1+1=2?"
                            dialog.showAndWait().ifPresent {
                                if(it.buttonData == ButtonBar.ButtonData.YES){
                                    dialog.contentText = "答对了"
                                }
                                if(it.buttonData == ButtonBar.ButtonData.NO){
                                    dialog.contentText = "答错了"
                                }
                            }
                            dialog.dialogPane.buttonTypes.apply {
                                remove(ButtonType.YES)
                                remove(ButtonType.NO)
                                add(ButtonType.FINISH)
                            }
                            dialog.title = "结果"
                            dialog.width = 400.0
                            dialog.height = 400.0
                            dialog.show()
                        }
                ).preSize(500,500)
        )
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(DialogDemo::class.java)
        }
    }

}