package dnf

import base.utils.DmUtils
import base.utils.Key
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
            while (true){
                if(check(findPic(0,751,77,818,"进入图1.bmp", "101010", 0.9))){
                    buff()
                    s(100)
                    run(Dir.RIGHT, 1790)
                    s(50)
                    keyPress(Key.r)
                    s(1000)
                    keyDown(Key.right)
                    keyDown(Key.down)
                    s(2000)
                    keyUp(Key.right)
                    keyUp(Key.down)
                    keyPress(Key.a)
                    s(50)
                    run(Dir.LEFT, 500)
                    room2()
                    return@Thread
                }
                s(100)
            }
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
            keepDownKey("5000", Key.right, Key.x)
            goToCornerAndNextRoom()
            keepDownKey("2000", Key.right)
            keyPressDelay(Key.q, 100)
            keepDownKey("3000", Key.t)
            keepDownKey("5000", Key.right, Key.down)
            keepDownKey("700", Key.left)
            keyPress(Key.e)
            s(2000)
            keepDownKey("300", Key.up)
            keyPress(Key.f)
            s(1000)
            keepDownKey("3000", Key.left, Key.down)
            keepDownKey("1000", Key.up)
            keyPressDelay(Key.d,100)
            keyPressDelay(Key.h,100)
            keepDownKey("2000", Key.down)
//            while (true){
//                if(checkAndClick(findPic(1000,0,1200,100,"进入图3.bmp","101010", 0.9))){
//                    keyUp(Key.x)
//                    s(100)6
//                    keyDown(Key.t)
//                    s(3000)
//                    keyUp(Key.t)
//                }
//                Thread.sleep(500)
//            }
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
            Dir.UP->Key.up
            Dir.DOWN->Key.down
            Dir.LEFT->Key.left
            Dir.RIGHT->Key.right
        }
        keyPress(key)
        Thread.sleep(50)
        keyPress(key)
        Thread.sleep(50)
        keyDown(key)
        Thread.sleep(time)
        keyUp(key)
    }

    /**
     * 移到角落然后前往下个房间
     */
    fun Dispatch.goToCornerAndNextRoom(){
        keepDownKey("2000", Key.right, Key.down)
        keepDownKey("2000", Key.up)
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

    fun Dispatch.keyPressDelay(kc:Int, time: Long){
        s(time)
        keyPress(kc)
        s(time)
    }



}