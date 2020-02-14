package base.utils

import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.Mnemonic
import java.util.*

interface CommonUtils {
    /**
     * 运行应用程序
     */
    fun runApp(appPath:String): Process? {
        val runtime  = Runtime.getRuntime()
        val process = runtime.exec(appPath)
        return process
    }

    fun Thread.startThread(): Thread {
        this.start()
        return this
    }

    fun Any.pln(){
        println(this.toString())
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


}