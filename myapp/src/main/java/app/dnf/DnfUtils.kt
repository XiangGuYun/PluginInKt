package app.dnf

import base.utils.DmUtils
import base.constant.Key
import base.utils.CommonUtils
import com.jacob.com.Dispatch
import java.awt.Robot
import java.awt.event.InputEvent

interface DnfUtils : DmUtils, CommonUtils {

    /**
     * 绑定DNF窗口
     */
    fun Dispatch.bindDNF(): Boolean {
        return Dispatch.call(this, "BindWindowEx", findWindow("地下城与勇士", "地下城与勇士"),
                "dx.graphic.3d",
                "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.state.api|dx.mouse.state.message|dx.mouse.api|dx.mouse.cursor|dx.mouse.raw.input|dx.mouse.input.lock.api2|dx.mouse.input.lock.api3",
//                "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.state.api|dx.mouse.api|dx.mouse.cursor",//
                "dx.keypad.input.lock.api|dx.keypad.state.api|dx.keypad.api|dx.keypad.raw.input", "dx.public.active.api|dx.public.active.message|dx.public.hide.dll|dx.public.input.ime|dx.public.graphic.protect|dx.public.anti.api|dx.public.km.protect|dx.public.prevent.block|dx.public.down.cpu", 0).int == 1
    }

    /**
     * 枚举所有的DNF窗口，返回窗口句柄集合
     */
    fun Dispatch.enumDnfWindows(): List<Int> {
        val result = this.enumWindow(0, "地下城与勇士", "地下城与勇士", 2 + 32)
        return if (result.isNotEmpty())
            result.split(",").map {
                it.toInt()
            }
        else
            listOf()

    }

    enum class Dir {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * 角色加BUFF
     */
    fun Dispatch.buff() {
        keyPress(Key.right)
        Thread.sleep(50)
        keyPress(Key.right)
        Thread.sleep(50)
        keyPress(Key.space)
    }

    /**
     * 角色奔跑
     */
    fun Dispatch.run(dir: Dir = Dir.RIGHT, time: Long = 2000) {
        val key = when (dir) {
            Dir.UP -> Key.up
            Dir.DOWN -> Key.down
            Dir.LEFT -> Key.left
            Dir.RIGHT -> Key.right
        }
        keyPress(key)
        Thread.sleep(30)
        keyPress(key)
        Thread.sleep(30)
        keyDown(key)
        Thread.sleep(time)
        keyUp(key)
    }

    /**
     * 持续按住某键
     */
    fun Dispatch.keepDownKey(keepTime: Long, vararg keyCodes: Int) {
        keyCodes.forEach {
            keyDown(it)
        }
        s(keepTime)
        keyCodes.forEach {
            keyUp(it)
        }
    }

    /**
     * 角色奔跑
     */
    fun Dispatch.keepDownKeyRun(keepTime: Long, vararg keyCodes: Int) {
        keyCodes.forEach {
            keyPress(it)
            s(30)
            keyDown(it)
        }
        s(keepTime.toLong())
        keyCodes.forEach {
            keyUp(it)
        }
    }

    /**
     * 按下某键位，并在按下的前后添加延迟时间
     */
    fun Dispatch.keyPressDelay(kc: Int, time: Long) {
        s(time)
        keyPress(kc)
        s(time)
    }

    /**
     * 自动拾取，注意必须使用中文输入法，否则无效(中文状态和英文状态不影响)
     */
    fun Dispatch.autoPick() {
        this.keyPress(Key.enter)
        s(100)
        this.sendString2(this.findWindow("地下城与勇士", "地下城与勇士"), "//移动物品")
        s(100)
        this.keyPress(Key.enter)
    }

    /**
     * 向左走一段时间
     */
    fun Dispatch.walkLeft(time: Int) {
        this.keepDownKey(time.toLong(), Key.left)
    }

    /**
     * 向上走一段时间
     */
    fun Dispatch.walkUp(time: Int) {
        this.keepDownKey(time.toLong(), Key.up)
    }

    /**
     * 向右走一段时间
     */
    fun Dispatch.walkRight(time: Int) {
        this.keepDownKey(time.toLong(), Key.right)
    }

    /**
     * 向下走一段时间
     */
    fun Dispatch.walkDown(time: Int) {
        this.keepDownKey(time.toLong(), Key.down)
    }

    /**
     * 向左跑一段时间
     */
    fun Dispatch.runLeft(time: Int) {
        this.keepDownKeyRun(time.toLong(), Key.left)
    }

    /**
     * 向上跑一段时间
     */
    fun Dispatch.runUp(time: Int) {
        this.keepDownKeyRun(time.toLong(), Key.up)
    }

    /**
     * 向右跑一段时间
     */
    fun Dispatch.runRight(time: Int) {
        this.keepDownKeyRun(time.toLong(), Key.right)
    }

    /**
     * 向下跑一段时间
     */
    fun Dispatch.runDown(time: Int) {
        this.keepDownKeyRun(time.toLong(), Key.down)
    }

    /**
     * 打开菜单并进入选择角色界面
     */
    fun Dispatch.goToCharacterPage() {
        this.keyPress(Key.esc)
        s(100)
        this.moveTo(682*2/3, 679*2/3)
        s(100)
        this.leftDoubleClick()
    }

    /**
     * 选择角色，目前仅限于1~12号
     */
    fun Dispatch.selectCharacter(pos: Int) {
        if (pos < 1) return
        this.moveTo(140*2/3 + 180*2/3 * ((pos - 1) % 6), 300*2/3 + ((pos - 1) / 6) * 300*2/3)
        s(100)
        this.leftDoubleClick()
        s(100)
        this.moveTo(700*2/3, 800*2/3)
        s(100)
        this.leftDoubleClick()
    }


}