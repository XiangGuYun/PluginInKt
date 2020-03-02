package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 大漠后台模块
 */
interface BackgroundUtils {

    enum class Display {
        NORMAL,
        GDI,
        GDI2
    }

    enum class Mouse {
        NORMAL,
        WINDOWS,
        WINDOWS2,
        WINDOWS3
    }

    enum class Keyboard {
        NORMAL,
        WINDOWS
    }

    /**
     * 绑定窗口
     */
    fun Dispatch.bindWindow(hwnd: Int, display: Display, mouse: Mouse, keyboard: Keyboard): Boolean {
        return Dispatch.call(this, "BindWindowEx", hwnd,
                when (display) {
                    Display.NORMAL -> "normal"
                    Display.GDI -> "gdi"
                    else -> "gdi2"
                },
                when (mouse) {
                    Mouse.NORMAL -> "normal"
                    Mouse.WINDOWS -> "windows"
                    Mouse.WINDOWS2 -> "windows2"
                    else -> "windows3"
                },
                when (keyboard) {
                    Keyboard.NORMAL -> "normal"
                    Keyboard.WINDOWS -> "windows"
                    else -> "dx.public.active.api|dx.public.active.message| dx.keypad.state.api|dx.keypad.api|dx.keypad.input.lock.api"
                },
                null,
                0
        ).int == 1
    }

    fun Dispatch.enableFakeActive(enable: Boolean): Boolean {
        return Dispatch.call(this, "EnableFakeActive", if (enable) 1 else 0).int == 1
    }

    /**
     * 解除绑定窗口,并释放系统资源
     */
    fun Dispatch.unBindWindow(): Boolean {
        return Dispatch.call(this, "UnBindWindow").int == 1
    }

}