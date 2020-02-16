package base.view

import base.constant.AppTitle
import base.constant.Style
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.StageStyle
import sample.base.BaseApp
import java.io.File

/**
 * requestWidth与width的区别？
 * 前者返回请求的宽高，后者返回真实宽高。
 * 注意：请求宽高指的是加载进内存的图片宽高，一旦指定后，图片的质量将不可在后续操作中更改
 */
@Style(StageStyle.UTILITY)
@AppTitle("ImageDemo")
class ImageDemo : BaseApp(){

    override fun init(window: Window) {
        val imgIs = File("C:\\Users\\Administrator\\bg.jpg").inputStream()

        //根据文件流加载图片
//        val img = Image(imgIs)
//        val img = Image(imgIs,
//                600.0,600.0,//指定图片的宽高
//                true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
//                true//当进行指定宽高缩放时是否进行平滑处理
//        )

        //根据url来加载网络图片
        val img = Image("http://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&f=JPEG?w=1280&h=853",
                600.0,600.0,//指定图片的宽高
                true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
                true,//当进行指定宽高缩放时是否进行平滑处理
                        true//是否异步加载
                )

        //加载resources下的文件
//        val img = Image("image/pvz.jpg",
//        600.0,600.0,//指定图片的宽高
//        true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
//        true//当进行指定宽高缩放时是否进行平滑处理
//        )

        //根据图片本地路径来加载图片
//        val img = Image("file:C:\\Users\\Administrator\\green.jpg",
//        600.0,600.0,//指定图片的宽高
//        true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
//        true//当进行指定宽高缩放时是否进行平滑处理
//        )

        //根据类加载器来加载图片1
//        val img = Image(javaClass.classLoader.getResourceAsStream("image/pvz.jpg"),
//                600.0,600.0,//指定图片的宽高
//                true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
//                true//当进行指定宽高缩放时是否进行平滑处理
//        )

        //根据类加载器来加载图片2
//        val img = Image(javaClass.classLoader.getResource("image/pvz.jpg").toExternalForm(),
//                600.0,600.0,//指定图片的宽高
//                true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
//                true//当进行指定宽高缩放时是否进行平滑处理
//        )

        //监听错误
        img.errorProperty().addListener { observable, oldValue, newValue ->
            newValue.pln("是否错误？")
        }

        //监听异常
        img.exceptionProperty().addListener { observable, oldValue, newValue ->
            newValue.message?.pln("是否异常？")
        }

        img.progressProperty().addListener { observable, oldValue, newValue ->
            newValue.pln("加载进度：")
        }

        window.scene = Scene(VBox().addChildren(ImageView(img)))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ImageDemo::class.java)
        }
    }

}