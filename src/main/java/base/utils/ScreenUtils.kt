package base.utils

import javafx.stage.Screen

interface ScreenUtils {

    /**
     * 获取屏幕的宽高信息
     * @isVisual 是否扣除状态栏的高度
     */
    fun getScreenSize(isVisual:Boolean = false): Pair<Int, Int> {
        return when {
            !isVisual -> {
                Screen.getPrimary().bounds.width.toInt() to Screen.getPrimary().bounds.height.toInt()
            }
            else -> {
                Screen.getPrimary().visualBounds.width.toInt() to Screen.getPrimary().visualBounds.height.toInt()
            }
        }
    }

    fun getScreenDpi(): Int {
        return Screen.getPrimary().dpi.toInt()
    }

}