package dnf

import base.utils.DmUtils
import base.constant.Key
import com.jacob.com.Dispatch

interface DnfUtils : DmUtils {

    /**
     * 绑定DNF窗口
     */
    fun Dispatch.bindDNF(): Boolean {
        return Dispatch.call(this, "BindWindowEx", findWindow("地下城与勇士", "地下城与勇士"),
                "dx.graphic.3d",
                "normal",//"dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.state.api|dx.mouse.api|dx.mouse.cursor",
                "dx.keypad.input.lock.api|dx.keypad.state.api|dx.keypad.api|dx.keypad.raw.input", "dx.public.active.api|dx.public.active.message|dx.public.hide.dll|dx.public.input.ime|dx.public.graphic.protect|dx.public.anti.api|dx.public.km.protect|dx.public.prevent.block|dx.public.down.cpu", 0).int == 1
    }

    /**
     * 亡命杀镇第1个房间
     */
    fun Dispatch.room1() {
        Thread {

        }.start()
    }

    /**
     * 亡命杀镇第2个房间
     */
    fun Dispatch.room2(){
        Thread{
            run(Dir.DOWN, 1200)
            keyPress(Key.w)
            s(500)
            run(Dir.RIGHT, 1000)
            keyPressDelay(Key.a, 10)
            goToCornerAndNextRoom()
            //第三图
            keepDownKeyRun("1000", Key.right)
            keepDownKey("3000", Key.t)
            keepDownKey("5250", Key.right, Key.down)
            //第四图
            keepDownKey("610", Key.left)
            keyPress(Key.e)
            s(2000)
            keyPress(Key.f)
            s(1000)
            keepDownKey("3000", Key.left, Key.down)
            keepDownKey("1100", Key.up)
            //第五图
            keyPressDelay(Key.h, 100) //45技能清怪
            keepDownKey("2900", Key.down)
            keepDownKey("300", Key.left)
            keyPressDelay(Key.g, 100)
            keepDownKey("1450", Key.right)
            keyPressDelay(Key.d, 100)
            keepDownKeyRun("3000", Key.right, Key.down)
            keepDownKeyRun("600", Key.up)
            run(Dir.RIGHT, 1000)
            //第六图
            keepDownKey("1000", Key.y)
            keepDownKeyRun("5100", Key.right, Key.down)
            keepDownKey("800", Key.right)
            keyPressDelay(Key.r, 100)
            while (!check(findPic(1100,200,1200,300, "是否继续.bmp", "101010", 0.9))){
                s(1000)
            }
            println("f1 ......")
            keyPressDelay(Key.f1, 1000)
        }.start()
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

    enum class Dir{
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * 角色奔跑
     */
    fun Dispatch.run(dir:Dir=Dir.RIGHT, time:Long=2000){
        val key = when(dir){
            Dir.UP-> Key.up
            Dir.DOWN-> Key.down
            Dir.LEFT-> Key.left
            Dir.RIGHT-> Key.right
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
     * 移到角落然后前往下个房间
     */
    fun Dispatch.goToCornerAndNextRoom(){
        keepDownKeyRun("3000", Key.right, Key.down)
        keepDownKeyRun("1500", Key.up)
    }

    /**
     * 持续按住某键
     */
    fun Dispatch.keepDownKey(keepTime:String, vararg keyCodes:Int){
        keyCodes.forEach {
            keyDown(it)
        }
        s(keepTime.toLong())
        keyCodes.forEach {
            keyUp(it)
        }
    }

    fun Dispatch.keepDownKeyRun(keepTime:String, vararg keyCodes:Int){
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

    fun Dispatch.keyPressDelay(kc:Int, time: Long){
        s(time)
        keyPress(kc)
        s(time)
    }



}