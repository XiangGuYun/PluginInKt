package base.jfx.view

import base.constant.AppTitle
import base.constant.Window
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.PixelFormat
import javafx.scene.layout.VBox
import sample.base.BaseApp

@AppTitle("ImageDemo1")
class ImageDemo1 : BaseApp() {

    override fun init(window: Window) {
        val img = Image("image/pvz.jpg",
                600.0, 600.0,//指定图片的宽高
                true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
                true,//当进行指定宽高缩放时是否进行平滑处理
                false//是否异步加载
        )
        val argb = img.pixelReader.getArgb(0, 0)
        val a = argb.shr(24) and 0xff
        val r = argb.shr(16) and 0xff
        val g = argb.shr(8) and 0xff
        val b = argb and 0xff
        "$a $r $g $b".pln()
        img.pixelReader.pixelFormat.type.name.pln()
        window.scene = Scene(VBox().addChildren(ImageView(img)))
        img.pixelReader.getColor(0, 0).let {
            (it.opacity * 255).pln()
            (it.red * 255).toInt().pln()
            (it.green * 255).toInt().pln()
            (it.blue * 255).toInt().pln()
            it.toString().pln()
            ("#" + it.toString().substring(2)).pln()
        }

        //一次性获取多像素值方案1
        val pf = PixelFormat.getByteBgraPreInstance()
        val ba = ByteArray(3 * 3 * 4) //一个像素4字节
        img.pixelReader.getPixels(0, 0, 3, 3, pf, ba, 0, 3 * 4)
        "方案1========================================".pln()
        for (i in ba.indices step 4) {
            val a = ba[i + 3].toInt() and 0xff
            val r = ba[i + 2].toInt() and 0xff
            val g = ba[i + 1].toInt() and 0xff
            val b = ba[i].toInt() and 0xff
            "$a $r $g $b".pln("***************")
        }

        //一次性获取多像素值方案2
        val pf1 = PixelFormat.getIntArgbPreInstance()
        val ba1 = IntArray(3 * 3 ) //以像素为单位
        img.pixelReader.getPixels(0, 0, 3, 3, pf1, ba1, 0, 3)
        "方案2========================================".pln()
        for (i in ba1.indices) {
            val a = ba1[i].shr(24) and 0xff
            val r = ba1[i].shr(16) and 0xff
            val g = ba1[i].shr(8) and 0xff
            val b = ba1[i] and 0xff
            "$a $r $g $b".pln("***************")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ImageDemo1::class.java)
        }
    }

}