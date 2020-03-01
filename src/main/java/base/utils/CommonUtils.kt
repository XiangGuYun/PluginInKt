package base.utils

import base.constant.Constant
import base.constant.JO
import base.jse.JseUtils
import base.utils.jfx.DialogUtils
import base.utils.jfx.MenuUtils
import base.utils.jfx.TextFieldUtils
import base.utils.jfx.WindowUtils
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.paint.Color
import sample.base.ViewUtils
import java.util.*

interface CommonUtils :  ViewUtils, DialogUtils, Constant, HKUtils, AdbUtils
        , WindowUtils, TextFieldUtils, MenuUtils, JseUtils, KeyUtils {
    /**
     * 运行应用程序
     */
    fun runApp(appPath:String): Process? {
        val runtime  = Runtime.getRuntime()
        val process = runtime.exec(appPath)
        return process
    }

    fun String.toResJfxPath(): String {
        return javaClass.classLoader.getResource(this).toExternalForm()
    }

    val String.file get() = "file:src/main/resources/$this"

    fun s(time: Int){
        Thread.sleep(time.toLong())
    }

    fun Thread.startThread(): Thread {
        this.start()
        return this
    }

    fun color(red:Int,green:Int,blue:Int,opacity:Double=1.0): Color {
        return Color.rgb(red,green,blue,opacity)
    }

    fun color(value:String,alpha:Double=1.0): Color {
        return Color.web(value,alpha)
    }

    fun Any.pln(pre:String=""){
        println(pre+this.toString())
    }

    fun <T> newFxList(): ObservableList<T> {
        return FXCollections.observableArrayList<T>()
    }

    fun Scene.setHotKey(keyCode:KeyCode, func:()->Unit, vararg km: KeyCombination.Modifier){
        val kc = KeyCodeCombination(keyCode, *km)
        accelerators[kc] = Runnable {
            func.invoke()
        }
    }

    fun Scene.setHotKey(key:String, func:()->Unit){
        val kc = KeyCodeCombination.valueOf(key)
        accelerators[kc] = Runnable {
            func.invoke()
        }
    }


    /**
     * 获取随机颜色
     */
    fun randomColor(): String {
        var r = Integer.toHexString(Random().nextInt(256)).toUpperCase()
        var g = Integer.toHexString(Random().nextInt(256)).toUpperCase()
        var b = Integer.toHexString(Random().nextInt(256)).toUpperCase()

        r = if (r.length == 1) "0$r" else r
        g = if (g.length == 1) "0$g" else g
        b = if (b.length == 1) "0$b" else b

        return "#${r + g + b}"
    }

    fun runOnMainThread(callback:()->Unit){
        Platform.runLater {
            callback.invoke()
        }
    }

    fun finish(){
        Platform.exit()
    }

    /**
     * 取随机数，取值范围为“原值-range ~ 原值"
     */
    fun Int.ru(range: Int = this/10): Int {
        return this + (1..range).shuffled().last()
    }

    /**
     * 取随机数，取值范围为“原值 ~ 原值+range"
     */
    fun Int.rd(range: Int = this/10): Int {
        return this - (1..range).shuffled().last()
    }

    /**
     * 取随机数，取值范围为“原值-range ~ 原值+range"
     */
    fun Int.r(range: Int = this/10): Int {
        return this - range + (1..range).shuffled().last()*2
    }

    val Int.r get() = this - this/10 + (1..this/10).shuffled().last()*2


}