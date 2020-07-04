package app.applet

import base.constant.*
import javafx.scene.image.Image
import javafx.stage.StageStyle
import sample.base.BaseApp

@Resizable(false)
@AppTitle("二维码生成")
@Style(StageStyle.DECORATED)
@LayoutId("qrcode")
class QrCodeApp : BaseApp(){

    override fun init(window: Window) {
        window.isAlwaysOnTop = true
        btn("get").clickBN {
            val text = tf("tf").text
            val destPath = "D:/qrcode.jpg"
            QRCodeUtil.encode(text, destPath)
            iv("iv").image = Image("file:$destPath",
                    380.0,380.0,//指定图片的宽高
                    true,//是否保留宽高比，当这个值为true时，多余的图片宽高将被删去
                    true,//当进行指定宽高缩放时是否进行平滑处理
                    true//是否异步加载
            )
        }
        btn("copy").clickBN {
           CopyUtils.copyImage(iv("iv").image)
        }
        btn("clear").clickBN {
            tf("tf").text = ""
        }
    }

    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            launch(QrCodeApp::class.java)
        }
    }

}