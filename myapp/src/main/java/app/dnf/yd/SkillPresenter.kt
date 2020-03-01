package app.dnf.yd

import base.utils.CommonUtils
import base.utils.DmUtils
import com.jacob.com.Dispatch
import java.util.concurrent.ExecutorService

interface SkillPresenter : CommonUtils, DmUtils {

    fun Dispatch.common() {
        while (YellowDragonApp.isBind && !YellowDragonApp.needPauseSkills) {
            this keyPress A
            s(300.r())
            this keyPress S
            s(300.r())
            this keyPress D
            s(300.r())
        }
    }

    fun Dispatch.hongYan() {
        while (YellowDragonApp.isBind && !YellowDragonApp.needPauseSkills) {
            this keyPress A
            s(150.r())
            this keyPress A
            s(150.r())
            this keyPress A
            s(150.r())
            this keyPress S
            s(300.r())
            this keyPress D
            s(300.r())
            this keyPress F
            s(300.r())
            this keyPress G
            s(300.r())
            this keyPress H
            s(300.r())
        }
    }

    fun Dispatch.ciKe() {
        while (YellowDragonApp.isBind && !YellowDragonApp.needPauseSkills) {
            this keyPress S
            s(100.r())
            this keyPress S
            s(100.r())
            this keyPress G
            s(1000.r())
        }
    }

    private fun Dispatch.jianHun() {
        this keyPress A
        s(100.r())
        this keyPress A
        s(100.r())
        this keyPress S
        s(100.r())
        this keyPress D
        s(100.r())
        this keyPress F
        s(100.r())
        this keyPress G
        s(100.r())
        this keyPress H
        s(100.r())
    }

    fun Dispatch.jianHunEx(cacheService: ExecutorService) {
        cacheService.submit {
            while (YellowDragonApp.isBind && !YellowDragonApp.needPauseSkills) {
                this.jianHun()
            }
        }
        cacheService.submit {
            while (YellowDragonApp.isBind && !YellowDragonApp.needPauseSkills) {
                this keyPress Q
                s(1000)
            }
        }
        cacheService.submit {
            while (YellowDragonApp.isBind && !YellowDragonApp.needPauseSkills) {
                this keyPress W
                s(200)
            }
        }
        cacheService.submit {
            while (YellowDragonApp.isBind && !YellowDragonApp.needPauseSkills) {
                this keyPress RIGHT
                this keyPress E
                s(100)
                this keyPress LEFT
                this keyPress E
                s(100)
                this keyPress RIGHT
                this keyPress E
                s(100)
            }
        }
    }

}