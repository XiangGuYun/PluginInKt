package app.dnf

import base.constant.Key
import base.utils.CommonUtils
import base.utils.DmUtils
import com.jacob.com.Dispatch

interface DnfUtils : DmUtils, CommonUtils {

    /**
     * 绑定DNF窗口
     */
    fun Dispatch.bindDNF(): Boolean {
        return Dispatch.call(this, "BindWindowEx", findWindow("地下城与勇士", "地下城与勇士"),
                "dx.graphic.3d",
//                "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.state.api|dx.mouse.state.message|dx.mouse.api|dx.mouse.cursor|dx.mouse.raw.input|dx.mouse.input.lock.api2|dx.mouse.input.lock.api3",
                "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.state.api|dx.mouse.api|dx.mouse.cursor",//
                "dx.keypad.input.lock.api|dx.keypad.state.api|dx.keypad.api|dx.keypad.raw.input",//|
                "dx.public.active.api|dx.public.active.message|dx.public.hide.dll|dx.public.input.ime|dx.public.graphic.protect|dx.public.anti.api|dx.public.km.protect|dx.public.prevent.block|dx.public.down.cpu",
                0).int == 1
    }

    /**
     * 绑定DNF窗口，需要传入窗口句柄
     */
    fun Dispatch.bindDNF(wh:Int): Boolean {
        return Dispatch.call(this, "BindWindowEx", wh,
                "dx.graphic.3d",
//                "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.state.api|dx.mouse.state.message|dx.mouse.api|dx.mouse.cursor|dx.mouse.raw.input|dx.mouse.input.lock.api2|dx.mouse.input.lock.api3",
                "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.state.api|dx.mouse.api|dx.mouse.cursor",//
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
        keyPress(RIGHT)
        s(50.r())
        keyPress(RIGHT)
        s(50.r())
        keyPress(Key.space)
    }

    /**
     * 角色奔跑
     */
    fun Dispatch.run(dir: Dir = Dir.RIGHT, time: Long = 2000) {
        val key = when (dir) {
            Dir.UP -> UP
            Dir.DOWN -> DOWN
            Dir.LEFT -> LEFT
            Dir.RIGHT -> RIGHT
        }
        keyPress(key)
        s(30.r())
        keyPress(key)
        s(30.r())
        keyDown(key)
        s(time.toInt())
        keyUp(key)
    }

    /**
     * 持续按住某键
     */
    fun Dispatch.keepDownKey(keepTime: Long, vararg keyCodes: Int) {
        keyCodes.forEach {
            keyDown(it)
        }
        s(keepTime.toInt())
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
        s(keepTime.toInt())
        keyCodes.forEach {
            keyUp(it)
        }
    }

    /**
     * 按下某键位，并在按下的前后添加延迟时间
     */
    fun Dispatch.keyPressDelay(kc: Int, time: Int) {
        s(time)
        keyPress(kc)
        s(time)
    }

    /**
     * 自动拾取，注意必须使用中文输入法，否则无效(中文状态和英文状态不影响)
     */
    fun Dispatch.autoPick() {
        this.keyPress(Key.enter)
        s(100.r())
        this.sendString2(this.findWindow("地下城与勇士", "地下城与勇士"), "//移动物品")
        s(100.r())
        this.keyPress(Key.enter)
    }

    /**
     * 向左走一段时间
     */
    fun Dispatch.walkLeft(time: Int) {
        this.keepDownKey(time.toLong(), LEFT)
    }

    /**
     * 向上走一段时间
     */
    fun Dispatch.walkUp(time: Int) {
        this.keepDownKey(time.toLong(), UP)
    }

    /**
     * 向右走一段时间
     */
    fun Dispatch.walkRight(time: Int) {
        this.keepDownKey(time.toLong(), RIGHT)
    }

    /**
     * 向下走一段时间
     */
    fun Dispatch.walkDown(time: Int) {
        this.keepDownKey(time.toLong(), DOWN)
    }

    /**
     * 向左上走一段时间
     */
    fun Dispatch.walkLeftUp(time: Int) {
        this.keepDownKey(time.toLong(), LEFT, UP)
    }

    /**
     * 向右上走一段时间
     */
    fun Dispatch.walkRightUp(time: Int) {
        this.keepDownKey(time.toLong(), RIGHT, UP)
    }

    /**
     * 向左下走一段时间
     */
    fun Dispatch.walkLeftDown(time: Int) {
        this.keepDownKey(time.toLong(), LEFT, DOWN)
    }

    /**
     * 向右下走一段时间
     */
    fun Dispatch.walkRightDown(time: Int) {
        this.keepDownKey(time.toLong(), RIGHT, DOWN)
    }

    /**
     * 向左跑一段时间
     */
    fun Dispatch.runLeft(time: Int) {
        this.keepDownKeyRun(time.toLong(), LEFT)
    }

    /**
     * 向上跑一段时间
     */
    fun Dispatch.runUp(time: Int) {
        this.keepDownKeyRun(time.toLong(), UP)
    }

    /**
     * 向右跑一段时间
     */
    fun Dispatch.runRight(time: Int) {
        this.keepDownKeyRun(time.toLong(), RIGHT)
    }

    /**
     * 向下跑一段时间
     */
    fun Dispatch.runDown(time: Int) {
        this.keepDownKeyRun(time.toLong(), DOWN)
    }

    /**
     * 向左上跑一段时间
     */
    fun Dispatch.runLeftUp(time: Int) {
        this.keepDownKeyRun(time.toLong(), LEFT, UP)
    }

    /**
     * 向左上跑一段时间
     */
    fun Dispatch.runRightUp(time: Int) {
        this.keepDownKeyRun(time.toLong(), RIGHT, UP)
    }

    /**
     * 向左下跑一段时间
     */
    fun Dispatch.runLeftDown(time: Int) {
        this.keepDownKeyRun(time.toLong(), LEFT, DOWN)
    }

    /**
     * 向右下跑一段时间
     */
    fun Dispatch.runRightDown(time: Int) {
        this.keepDownKeyRun(time.toLong(),RIGHT, DOWN)
    }

    /**
     * 打开菜单并进入选择角色界面
     */
    fun Dispatch.goToCharacterPage() {
        this.keyPress(Key.esc)
        s(100.r())
        this.moveTo(682 * 2 / 3, 679 * 2 / 3)
        s(100.r())
        this.leftDoubleClick()
    }

    /**
     * 返回城镇
     */
    fun Dispatch.backToTown() {
        this.keyPress(Key.esc)
        s(100.r())
        this.moveTo(580,440)
        s(100.r())
        this.leftDoubleClick()
    }

    /**
     * 选择角色，目前仅限于1~12号
     */
    fun Dispatch.selectCharacter(pos: Int) {
        if (pos < 1) return
        this.moveTo(140 * 2 / 3 + 180 * 2 / 3 * ((pos - 1) % 6), 300 * 2 / 3 + ((pos - 1) / 6) * 300 * 2 / 3)
        s(100.r())
        this.leftDoubleClick()
        s(100.r())
        this.moveTo(700 * 2 / 3, 800 * 2 / 3)
        s(100.r())
        this.leftDoubleClick()
    }

    /**
     * 选择下一个角色
     */
    fun Dispatch.selectNextCharacter(){
        this.keyPress(RIGHT)
        s(1000.r())
        this.moveTo(700 * 2 / 3, 800 * 2 / 3)
        s(100.r())
        this.leftDoubleClick()
    }

    /**
     * 检查是否卡屏，可判断左上角和右上角
     */
    fun Dispatch.checkIsDeadDisplay(isGoLeftTop:Boolean, sec:Int, size:Int=30): Boolean {
        return if(isGoLeftTop)
            this.IsDisplayDead(960-size, 0, 960,size, sec)
        else
            this.IsDisplayDead(0, 0, size,size, sec)
    }

    /**
     * 检查是否卡屏
     */
    fun Dispatch.checkIsDeadDisplay(dir:Int, sec:Int, size:Int=30): Boolean {
        return when(dir) {
             1 -> this.IsDisplayDead(0, 0, size,size, sec)
             2 -> this.IsDisplayDead(960-size, 0, 960,size, sec)
             3 -> this.IsDisplayDead(0, 0, size,size, sec)
             else -> this.IsDisplayDead(0, 0, size,size, sec)
        }
    }


}