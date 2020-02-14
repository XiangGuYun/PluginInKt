package base.utils

import javafx.scene.control.TextField
import javafx.scene.control.Tooltip

/**
 * 输入框
 * 常用子类：PasswordField
 */
interface TextFieldUtils {

    fun TextField.hint(hint:String): TextField {
        promptText = hint
        return this
    }

    fun TextField.tip(hint:String): TextField {
        tooltip = Tooltip(hint)
        return this
    }

    /**
     * 添加输入监听事件
     *
     * 限制输入的字符数量
     * if(newValue.length > 6){
     *    ta.text = oldValue
     * }
     */
    fun TextField.textWatcher(callback:(ta:TextField, oldValue:String, newValue:String)->Unit): TextField {
        textProperty().addListener { observable, oldValue, newValue ->
            callback.invoke(this, oldValue, newValue)
        }
        return this
    }

    /**
     * 监听选中的文字
     */
    fun TextField.textSelected(callback:(ta:TextField, oldValue:String, newValue:String)->Unit): TextField {
        selectionProperty().addListener { observable, oldValue, newValue ->
            callback.invoke(this, oldValue.toString(), newValue.toString())
        }
        return this
    }

}