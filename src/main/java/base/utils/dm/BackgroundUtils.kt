package base.utils.dm

import com.jacob.com.Dispatch

interface BackgroundUtils {
    enum class Display {
        NORMAL,
        GDI,
        GDI2,
        DX2,
        DX3,
        DX
    }

    enum class Mouse {
        NORMAL,
        WINDOWS,
        WINDOWS2,
        WINDOWS3,
        DX,
        DX2
    }

    enum class Keyboard {
        NORMAL,
        WINDOWS,
        DX
    }

    /**
     * 绑定窗口
     */
    fun Dispatch.bindWindow(hwnd: Int, display: Display, mouse: Mouse, keyboard: Keyboard): Boolean {
        return Dispatch.call(this, "BindWindowEx", hwnd,
                when (display) {
                    Display.NORMAL -> "normal"
                    Display.GDI -> "gdi"
                    Display.GDI2 -> "gdi2"
                    Display.DX2 -> "dx2"
                    Display.DX3 -> "dx3"
                    else -> "dx.graphic.3d"
                },
                when (mouse) {
                    Mouse.NORMAL -> "normal"
                    Mouse.WINDOWS -> "windows"
                    Mouse.WINDOWS2 -> "windows2"
                    Mouse.WINDOWS3 -> "windows3"
                    Mouse.DX -> "dx"
                    else -> "dx2"
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