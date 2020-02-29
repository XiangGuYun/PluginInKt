package base.utils

import com.melloware.jintellitype.JIntellitype
import javafx.application.Platform

/**
 * 注册热键
 */
interface HKUtils {

    /*
    regHK(1, Key.f1)

    regHK(2, Key.f, CTRL+SHIFT)
    2
    setHKListener {
        when(it){
            1-> {
                getProcessList().forEach {
                    println(it.first+" "+it.second)
                }
            }
            2->{
                alert("这不是爱")
            }
        }
    }
     */

    val _CTRL get() = JIntellitype.MOD_CONTROL
    val _SHIFT get() = JIntellitype.MOD_SHIFT
    val _ALT get() = JIntellitype.MOD_ALT
    val _WIN get() = JIntellitype.MOD_WIN

    /**
     * 注册热键
     * @param id 标志
     * @param mainKeyCode 主键键代码
     * @param combKeyCode 组合键键代码，多个用+相连
     */
    fun regHK(id:Int, mainKeyCode:Int, combKeyCode:Int = 0){
        JIntellitype.getInstance().registerHotKey(id, combKeyCode, mainKeyCode)
    }

    /**
     * 热键事件监听
     */
    fun setHKListener(callback:(Int)->Unit){
        JIntellitype.getInstance().addHotKeyListener { id ->
            Platform.runLater {
                callback.invoke(id)
            }
        }
    }

}