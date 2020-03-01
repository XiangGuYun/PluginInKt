package base.utils

import base.utils.dm.*
import com.jacob.activeX.ActiveXComponent
import com.jacob.com.Dispatch
import java.awt.MouseInfo
import java.awt.Point


interface DmUtils : WindowUtils, KeyboardMouseUtils, PictureColorUtils, BackgroundUtils, BasicSettingUtils,
        MemoryUtils, CharRecognitionUtils, SystemUtils {

    /**
     * 初始化大漠插件
     */
    fun initDmCom(): Dispatch {
        return ActiveXComponent("dm.dmsoft").`object`
    }
    
    /**
     * 绑定雷电模拟器
     */
    fun Dispatch.bindLDMonitor(title: String = "雷电模拟器"): Boolean {
        val wh1 = this.findWindow("LDPlayerMainFrame", title)
        val wh2 = this.findWindowEx(wh1, "RenderWindow", "TheRender")
        return this.bindWindow(wh2, BackgroundUtils.Display.NORMAL, BackgroundUtils.Mouse.WINDOWS,
                BackgroundUtils.Keyboard.WINDOWS)
    }

    /**
     * 检查是否能找到图片，是则点击此处并返回true
     */
    fun Dispatch.checkAndClick(result: String): Boolean {
        if (check(result)) {
            this.click(result)
            return true
        }
        return false
    }

    /**
     * 检查是否能找到图片，是则双击此处并返回true
     */
    fun Dispatch.checkAndDoubleClick(result: String): Boolean {
        if (check(result)) {
            this.doubleClick(result)
            return true
        }
        return false
    }

    /**
     * 判断是否成功检测到图或字
     */
    fun check(result: String): Boolean {
        return result != "-1|-1|-1"
    }

    /**
     * 将扫描到的位置信息转换为坐标点
     */
    fun String.toPoint(): Point {
        val arr = this.split("|")
        return Point(arr[1].toInt(), arr[2].toInt())
    }

    /**
     * 移动鼠标到指定区域并点击
     */
    fun Dispatch.click(result: String) {
        val arr = result.split("|")
        Thread.sleep(100)
        this.moveTo(arr[1].toInt(), arr[2].toInt())
        Thread.sleep(100)
        this.leftClick()
    }

    /**
     * 移动并单击坐标处
     */
    fun Dispatch.click(x: Int, y: Int) {
        this.moveTo(x, y)
        Thread.sleep(100)
        this.leftClick()
    }

    /**
     * 移动并双击坐标处
     */
    fun Dispatch.doubleClick(result: String) {
        val arr = result.split("|")
        Thread.sleep(100)
        this.moveTo(arr[1].toInt(), arr[2].toInt())
        Thread.sleep(100)
        this.leftDoubleClick()
    }

}