package app.dm

import base.utils.DmUtils
import base.utils.Win32Utils
import base.constant.Window
import base.utils.dm.BackgroundUtils
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import sample.base.BaseApp

 class MainApp : BaseApp(), DmUtils, Win32Utils {

    override fun init(window: Window) {
        window.apply {
            title = "Java与大漠插件"
            isResizable = false
            icons.add(Image("image/sanguo.png"))
        }
        setContentView("layout/sample", 640, 380)

        val dm = initDmCom()

        val lv = ListView<Node>()

        val vb1 = VBox()
        val iv1 = ImageView().apply {
            fitWidth = 300.0
            fitHeight = 300.0
        }
        vb1.addChild(iv1)

        lv.addView(Label("截图").apply {
            id = "btn1"
            click {
                dm.capture(0, 300, 300, 600, "e:/javaPic.jpg")
                iv1.image = Image("file:e:/javaPic.jpg")
            }
        }.preSize(640,100).bgColor("#ff6666"))

        lv.addView(Label("截图").preSize(640,100).bgColor("#66ff66"))

        lv.addView(Label("截图").preSize(640,100).bgColor("#6666ff"))

        lv.addView(vb1)

        lv.addView(Button("获取坐标颜色值").apply {
            click {
                 alert(dm.getColor(100, 100))
            }
        })

        lv.addView(Button("获取记事本窗口宽高值").apply {
            click {
                val size = getWindowSize(null, "无标题 - 记事本")
                alert("${size.first}x${size.second}")
            }
        })

        lv.addView(Button("移动鼠标位置到(100, 100)").apply {
            click {
                dm.moveTo(100, 100)
            }
        })

        lv.addView(Button("offset鼠标位置10x10").apply {
            click {
                dm.moveR(10, 10)
            }
        })

        lv.addView(Button("移动鼠标到区域内的任意一点").apply {
            click {
                println(dm.moveToEx(0, 0, 100, 100))
            }
        })

        lv.addView(Button("移动到某个点再点击鼠标左键").apply {
            click {
                dm.moveTo(35, 50)
                dm.leftClick()
            }
        })

        lv.addView(Button("移动到某个点再双击鼠标左键").apply {
            click {
                dm.moveTo(35, 50)
                dm.leftDoubleClick()
            }
        })

        lv.addView(Button("移动到某个点再点击鼠标右键").apply {
            click {
                Thread{
                    dm.moveTo(35, 50)
                    Thread.sleep((1000..2000).shuffled().last().toLong())
                    dm.rightClick()
                }.start()
            }
        })

        lv.addView(Button("绑定窗口").apply {
            click {
                println("绑定窗口是否成功 ${dm.bindWindowLite(dm.findWindow(null, "无标题 - 记事本"), BackgroundUtils.Display.NORMAL,
                        BackgroundUtils.Mouse.WINDOWS, BackgroundUtils.Keyboard.WINDOWS)}")
                dm.capture(0, 0, 200, 200, "e:/notepad.jpg")
            }
        })

        lv.addView(Button("打开记事本").apply {
            click {
                runApp("c:\\windows\\notepad.exe")
            }
        })

        lv.addView(Button("设置剪贴板").apply {
            click {
                dm.setClipboard("你好呀！")
                println(dm.getClipboard())
            }
        })

        lv.addView(Button("设置记事本窗口标题").apply {
            click {
                val wh = dm.findWindow("Notepad", null)
                dm.setWindowText(wh, "新标题")
            }
        })

        //设置列表项点击事件
        lv.itemClick { newValue ->
            when {
                newValue.id == "btn1" -> {

                }
            }
        }

        vb("vb").addChild(lv)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(MainApp::class.java)
        }
    }

}
