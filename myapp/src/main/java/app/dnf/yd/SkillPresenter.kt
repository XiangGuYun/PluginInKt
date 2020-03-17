package app.dnf.yd

import app.dnf.DnfUtils
import app.dnf.yd.YellowDragonApp.Companion.needBreak
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

//    fun Dispatch.skillNvRouDao(){
//        runRight(50)
//        keepDownKey(100L, A)
//        while (YellowDragonApp.isBind.get()) {
//            if (!YellowDragonApp.needPauseSkills) {
//                if (needBreak) break
//                this keyPressIf S
//                sIf(100.r)
//                if (needBreak) break
//                this keyPressIf D
//                sIf(100.r)
//                if (needBreak) break
//                this keyPressIf A
//                sIf(100.r)
//                if (needBreak) break
//                this keyPressIf F
//                sIf(100.r)
//                if (needBreak) break
//                runRight(50)
//                keepDownKey(100L, A)
//                sIf(100.r)
//            }
//        }
//    }

    fun Dispatch.skillFengFa(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(400.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300.r)
                if (needBreak) break
                this keyPressIf F
                sIf(300.r)
            }
        }
    }

    fun Dispatch.skillNvRouDao(){
        runRight(50)
        keepDownKey(100L, A)
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf S
                sIf(100.r)
                if (needBreak) break
                this keyPressIf D
                sIf(100.r)
                if (needBreak) break
                this keyPressIf A
                sIf(100.r)
                if (needBreak) break
                this keyPressIf F
                sIf(100.r)
                if (needBreak) break
                runRight(50)
                keepDownKey(100L, A)
                sIf(100.r)
            }
        }
    }

    fun Dispatch.skillNvDaQiang(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(200.r)
                if (needBreak) break
                this keyPressIf S
                sIf(200.r)
                if (needBreak) break
                this keyPressIf D
                sIf(200.r)
                if (needBreak) break
                this keyPressIf F
                sIf(200.r)
            }
        }
    }

    fun Dispatch.skillNvManYou(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                runRight(100)
                if (needBreak) break
                this keyPressIf S
                sIf(200.r)
                if (needBreak) break
                this keyPressIf D
                sIf(200.r)
                if (needBreak) break
                this keyPressIf F
                sIf(200.r)
                if (needBreak) break
                this keyPressIf G
                sIf(200.r)
            }
        }
    }

    fun Dispatch.skillGuanYu(){
        runRight(100)
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf S
                sIf(50.r)
                if (needBreak) break
                this keyPressIf D
                sIf(50.r)
                if (needBreak) break
                this keyPressIf F
                sIf(50.r)
                if (needBreak) break
                this keyPressIf G
                sIf(300.r)
                if (needBreak) break
                this keyPressIf H
                sIf(50.r)
            }
        }
    }

    fun Dispatch.skillJianMo(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                runRight(300)
                sIf(100.r)
                if (needBreak) break
                this keyPressIf D
                runRight(300)
                sIf(100.r)
                if (needBreak) break
                this keyPressIf F
                runRight(300)
                sIf(100.r)
                if (needBreak) break
                this keyPressIf G
                runRight(300)
                sIf(100.r)
            }
        }
    }

    fun Dispatch.skillHongYan(){
        this.runRight(200)
        this.keepDownKey(1200L, A, RIGHT)
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf S
                sIf(100.r)
                if (needBreak) break
                this keyPressIf D
                sIf(100.r)
                if (needBreak) break
                this keyPressIf F
                sIf(100.r)
                if (needBreak) break
                this keyPressIf G
                sIf(100.r)
                this keyPressIf A
                sIf(100.r)
            }
        }
    }

    fun Dispatch.skillJianZong(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(500)
                if (needBreak) break
                this keyPressIf F
                sIf(100.r)
                if (needBreak) break
                this keyPressIf LEFT
                sIf(50.r)
                if (needBreak) break
                this keyPressIf G
                sIf(300.r)
            }
        }
    }

    fun Dispatch.skillAXiuLiu(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300)
                if (needBreak) break
                this keyPressIf F
                sIf(300.r)
                if (needBreak) break
                this keyPressIf G
                sIf(300.r)
            }
        }
    }

    fun Dispatch.skillBingJieShi(){
        for (i in 1..6) {
            if (i != 6) {
                this keyPressIf A
            } else {
                this keyPressIf S
                s(100.r)
                this keyPressIf S
            }
            s(100)
        }
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf F
                sIf(100.r())
                if (needBreak) break
                this keyPressIf D
                sIf(100.r())
                if (needBreak) break
                this keyPressIf S
                sIf(100.r())
                if (needBreak) break
                this keyPressIf A
                sIf(100.r())
            }
        }
    }

    fun Dispatch.skillJianHun(){
        runRight(200)
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300)
                if (needBreak) break
                this keyPressIf F
                sIf(300.r)
                if (needBreak) break
                this keyPressIf G
                sIf(300.r)
                if (needBreak) break
                this keyPressIf H
                sIf(300.r)
            }
        }
    }

    fun Dispatch.skillNaiBa(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300)
                if (needBreak) break
                this keyPressIf F
                sIf(300.r)
            }
        }
    }

    fun Dispatch.skillNvQiGong(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300)
            }
        }
    }

    fun Dispatch.skillNanJieBa(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf SPACE
                sIf(100)
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300)
            }
        }
    }

    fun Dispatch.skillCiKe(){
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                s(50)
                this keyPressIf A
                s(50)
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300)
            }
        }
    }

    fun Dispatch.skillGuiQi(){
        runRight(200)
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
                if (needBreak) break
                this keyPressIf S
                sIf(300.r)
                if (needBreak) break
                this keyPressIf D
                sIf(300)
            }
        }
    }

}