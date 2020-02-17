package base.jfx.view

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import sample.base.BaseApp

@AppTitle("ImageViewDemo")
class ImageViewDemo : BaseApp(){

    override fun init(window: Window) {
        val img = Image("http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853")
        val iv = ImageView(img).apply {
            isPreserveRatio = true //是否保持宽高比
            fitWidth = 600.0 //适合宽度
            fitHeight = 600.0 //适合高度
            isSmooth = true//是否平滑处理
        }
        window.scene = Scene(VBox().addChildren(iv))

        iv.fitWidth.pln()
        iv.fitHeight.pln()
        //获取实际高度
        iv.prefHeight(-1.0).pln()

        //设置圆角
        val rect = Rectangle(600.0, iv.prefHeight(-1.0)).apply{
            arcWidth = 50.0
            arcHeight = 50.0
        }
        iv.clip = rect
        //搭配clip使用，截取一块图片区域
//        iv.x = 400.0
//        iv.y = 200.0

        val w = iv.fitWidth
        val h = iv.prefHeight(-1.0)

        //截取一块区域并放大到fitWidth和fitHeight显示
//        val rect2d = Rectangle2D(w/3, h/3, w/3*2, h/3*2)
//        iv.viewport = rect2d
//
//        iv.setOnMouseDragged {
//            iv.viewport = Rectangle2D(it.sceneX, it.sceneY, w/3*2, h/3*2)
//        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ImageViewDemo::class.java)
        }
    }

}