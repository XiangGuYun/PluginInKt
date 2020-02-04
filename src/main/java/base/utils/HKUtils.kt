package base.utils

import com.melloware.jintellitype.JIntellitype
import javafx.application.Platform

/**
 * 注册热键
 */
interface HKUtils {

    val CTRL get() = JIntellitype.MOD_CONTROL
    val SHIFT get() = JIntellitype.MOD_SHIFT
    val ALT get() = JIntellitype.MOD_ALT
    val WIN get() = JIntellitype.MOD_WIN

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