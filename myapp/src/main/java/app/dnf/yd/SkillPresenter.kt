package app.dnf.yd

import app.dnf.DnfUtils
import base.utils.CommonUtils
import base.utils.DmUtils
import com.jacob.com.Dispatch
import java.util.concurrent.ExecutorService

interface SkillPresenter : CommonUtils, DnfUtils {

    infix fun Dispatch.keyPressIf(code:Int){
        if(!YellowDragonApp.needPauseSkills){
            this.keyPress(code)
        }
    }

    infix fun Dispatch.keyDownIf(code:Int){
        if(!YellowDragonApp.needPauseSkills){
            this.keepDownKey(1000L, code)
        }
    }

    fun sIf(time:Int){
        if(!YellowDragonApp.needPauseSkills){
            s(time.r())
        }
    }
    
    fun Dispatch.common() {
        while (YellowDragonApp.isBind.get()) {
            if(!YellowDragonApp.needPauseSkills){
                this keyPressIf A
                sIf(300.r())
                this keyPressIf S
                sIf(300.r())
                this keyPressIf D
                sIf(300.r())
            }
        }
    }

    fun Dispatch.skillASDF(){
        while (YellowDragonApp.isBind.get()) {
            if(!YellowDragonApp.needPauseSkills){
                this keyPressIf A
                sIf(300.r)
                this keyPressIf S
                sIf(300.r)
                this keyPressIf D
                sIf(300.r)
            }
        }
    }

    fun Dispatch.skillASDFG(needRun:Boolean = false){
        while (YellowDragonApp.isBind.get()) {
            if(!YellowDragonApp.needPauseSkills){
                if(needRun) this.runRight(200)
                this keyPressIf A
                sIf(200.r())
                if(needRun) this.runRight(200)
                this keyPressIf S
                sIf(200.r())
                if(needRun) this.runRight(200)
                this keyPressIf D
                sIf(200.r())
                if(needRun) this.runRight(200)
                this keyPressIf F
                sIf(200.r())
                if(needRun) this.runRight(200)
                this keyPressIf G
                sIf(200.r())
//                this keyPressIf H
//                sIf(200.r())
            }
        }
    }

    fun Dispatch.hongYan() {
        while (YellowDragonApp.isBind.get()) {
            if(!YellowDragonApp.needPauseSkills){
                this keyPressIf A
                sIf(150.r())
                this keyPressIf A
                sIf(150.r())
                this keyPressIf A
                sIf(150.r())
                this keyPressIf S
                sIf(300.r())
                this keyPressIf D
                sIf(300.r())
                this keyPressIf F
                sIf(300.r())
                this keyPressIf G
                sIf(300.r())
                this keyPressIf H
                sIf(300.r())
            }
        }
    }

    private fun Dispatch.jianHun() {
        this keyPressIf A
        sIf(100.r())
        this keyPressIf A
        sIf(100.r())
        this keyPressIf S
        sIf(100.r())
        this keyPressIf D
        sIf(100.r())
        this keyPressIf F
        sIf(100.r())
        this keyPressIf G
        sIf(100.r())
        this keyPressIf H
        sIf(100.r())
    }

    fun Dispatch.jianHunEx(cacheService: ExecutorService) {
        cacheService.submit {
            while (YellowDragonApp.isBind.get()) {
                if(!YellowDragonApp.needPauseSkills) this.jianHun()
            }
        }
        cacheService.submit {
            while (YellowDragonApp.isBind.get()) {
                if(!YellowDragonApp.needPauseSkills){
                    this keyPressIf Q
                    sIf(1000)
                }
            }
        }
        cacheService.submit {
            while (YellowDragonApp.isBind.get()) {
                if(!YellowDragonApp.needPauseSkills){
                    this keyPressIf W
                    sIf(200)
                }
            }
        }
        cacheService.submit {
            while (YellowDragonApp.isBind.get()) {
                if(!YellowDragonApp.needPauseSkills){
                    this keyPressIf RIGHT
                    this keyPressIf E
                    sIf(100)
                    this keyPressIf LEFT
                    this keyPressIf E
                    sIf(100)
                    this keyPressIf RIGHT
                    this keyPressIf E
                    sIf(100)
                }
            }
        }
    }

}