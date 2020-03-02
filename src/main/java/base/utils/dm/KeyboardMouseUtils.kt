package base.utils.dm

import com.jacob.com.Dispatch
import java.awt.MouseInfo
import java.awt.Point

/**
 * 大漠键鼠模块
 */
interface KeyboardMouseUtils {

    /**
     * 获取鼠标当前位置
     * @return Point 包含x和y坐标
     */
    fun getCursorPos(): Point {
        val pi = MouseInfo.getPointerInfo()
        val p = pi.location
        val x = p.getX()
        val y = p.getY()
        return Point(x.toInt(), y.toInt())
    }

    /**
     * 按住某键（根据按键码）
     */
    infix fun Dispatch.keyDown(vk_code: Int): Boolean {
        return Dispatch.call(this, "KeyDown", vk_code).int == 1
    }

    /**
     * 按住某键（根据键位上的字符，例如enter、1、F1、a、B，不区分大小写）
     */
    infix fun Dispatch.keyDownChar(vk_str: String): Boolean {
        return Dispatch.call(this, "KeyDownChar", vk_str).int == 1
    }

    /**
     * 按下某键（根据按键码）
     * @receiver Dispatch
     * @param vk_code Int 按键码
     * @return Boolean 是否成功
     */
    infix fun Dispatch.keyPress(vk_code: Int): Boolean {
        return Dispatch.call(this, "KeyPress", vk_code).int == 1
    }

    /**
     * 按下某键（根据键位上的字符，例如enter、1、F1、a、B，不区分大小写）
     */
    infix fun Dispatch.keyPressChar(vk_str: String): Boolean {
        return Dispatch.call(this, "KeyPressChar", vk_str).int == 1
    }

    /**
     * 根据指定的字符串序列，依次按顺序按下其中的字符
     * @param delay 按下字符后的间隔时间，单位是毫秒
     */
    fun Dispatch.keyPressStr(vk_str: String, delay: Int): Boolean {
        return Dispatch.call(this, "KeyPressStr", vk_str, delay).int == 1
    }

    /**
     * 松开某键（根据按键码）
     */
    infix fun Dispatch.keyUp(vk_code: Int): Boolean {
        return Dispatch.call(this, "KeyUp", vk_code).int == 1
    }

    /**
     * 松开某键（根据键位上的字符，例如enter、1、F1、a、B，不区分大小写）
     */
    infix fun Dispatch.keyUpChar(vk_str: String): Boolean {
        return Dispatch.call(this, "KeyUpChar", vk_str).int == 1
    }

    /**
     * 单击鼠标左键
     */
    fun Dispatch.leftClick(): Boolean {
        return Dispatch.call(this, "LeftClick").int == 1
    }

    /**
     * 双击鼠标左键
     */
    fun Dispatch.leftDoubleClick(): Boolean {
        return Dispatch.call(this, "LeftDoubleClick").int == 1
    }

    /**
     * 按住鼠标左键
     */
    fun Dispatch.leftDown(): Boolean {
        return Dispatch.call(this, "LeftDown").int == 1
    }

    /**
     * 松开鼠标左键
     */
    fun Dispatch.leftUp(): Boolean {
        return Dispatch.call(this, "LeftUp").int == 1
    }

    /**
     * 按下鼠标滚轮
     */
    fun Dispatch.middleClick(): Boolean {
        return Dispatch.call(this, "MiddleClick").int == 1
    }

    /**
     * 移动鼠标到某坐标点
     */
    fun Dispatch.moveTo(x: Int, y: Int): Boolean {
        return Dispatch.call(this, "MoveTo", x, y).int == 1
    }

    /**
     * 偏移鼠标坐标点
     */
    fun Dispatch.moveR(x: Int, y: Int) {
        Dispatch.call(this, "MoveR", x, y)
    }

    /**
     * 移动鼠标到指定区域的随机一个点上
     */
    fun Dispatch.moveToEx(x: Int, y: Int, w: Int, h: Int): String {
        return Dispatch.call(this, "MoveToEx", x, y, w, h).string
    }

    /**
     * 按下鼠标右键
     */
    fun Dispatch.rightClick(): Boolean {
        return Dispatch.call(this, "RightClick").int == 1
    }

    /**
     * 按住鼠标右键
     */
    fun Dispatch.rightDown(): Boolean {
        return Dispatch.call(this, "RightDown").int == 1
    }

    /**
     * 松开鼠标右键
     */
    fun Dispatch.rightUp(): Boolean {
        return Dispatch.call(this, "RightUp").int == 1
    }

    /**
     * 键鼠类型
     * NORMAL：对应normal键盘，默认内部延时为30ms；对应normal鼠标，默认内部延时为30ms
     * WINDOWS：对应windows键盘，默认内部延时为10ms；对应windows鼠标，默认内部延时为10ms
     * DX：对应dx键盘，默认内部延时为50ms；对应dx鼠标，默认内部延时为40ms
     */
    enum class KMType {
        NORMAL, WINDOWS, DX
    }

    /**
     * 设置按键时，键盘按下和弹起的时间间隔
     */
    fun Dispatch.setKeypadDelay(type: KMType, delay: Int): Boolean {
        return Dispatch.call(this, "SetKeypadDelay",
                when (type) {
                    KMType.NORMAL -> "normal"
                    KMType.WINDOWS -> "windows"
                    else -> "dx"
                },
                delay).int == 1
    }

    /**
     * 设置鼠标时，鼠标按下和弹起的时间间隔
     */
    fun Dispatch.setMouseDelay(type: KMType, delay: Int): Boolean {
        return Dispatch.call(this, "SetMouseDelay",
                when (type) {
                    KMType.NORMAL -> "normal"
                    KMType.WINDOWS -> "windows"
                    else -> "dx"
                },
                delay).int == 1
    }

    /**
     * 等待指定的按键按下 (前台,不是后台)
     * @param time_out 等待多久，单位毫秒。如果是0，表示一直等待
     */
    fun Dispatch.waitKey(vk_code: Int, time_out: Int) {
        Dispatch.call(this, "WaitKey", vk_code, time_out)
    }

    /**
     * 滚轮向下滚
     */
    fun Dispatch.wheelDown(): Boolean {
        return Dispatch.call(this, "WheelDown").int == 1
    }

    /**
     * 滚轮向上滚
     */
    fun Dispatch.wheelUp(): Boolean {
        return Dispatch.call(this, "WheelUp").int == 1
    }
}