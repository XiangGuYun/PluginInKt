package app.dnf.yd

import app.dnf.DnfUtils
import app.dnf.yd.YellowDragonApp.Companion.findFengSheng
import app.dnf.yd.YellowDragonApp.Companion.findYueFei
import app.dnf.yd.YellowDragonApp.Companion.needBreak
import base.constant.AL
import base.utils.CommonUtils
import base.utils.DmUtils
import com.jacob.com.Dispatch
import java.util.concurrent.ExecutorService

interface SkillPresenter : CommonUtils, DnfUtils {

    infix fun Dispatch.keyPressIf(code: Int) {
        if (!YellowDragonApp.needPauseSkills) {
            this.keyPress(code)
        }
    }

    infix fun Dispatch.keyDownIf(code: Int) {
        if (!YellowDragonApp.needPauseSkills) {
            this.keepDownKey(1000L, code)
        }
    }

    fun sIf(time: Int) {
        if (!YellowDragonApp.needPauseSkills) {
            s(time.r())
        }
    }

    fun Dispatch.skillFengFa() {
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

    fun Dispatch.skillTeGong() {
        while (YellowDragonApp.isBind.get()) {
            if(findFengSheng){
                runRight(500)
            }
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(300.r)
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

    fun Dispatch.skillSiLing() {
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
                sIf(100.r)
                if (needBreak) break
                this keyPressIf F
                sIf(100.r)
                if (needBreak) break
                this keyPressIf G
                sIf(100.r)
            }
        }
    }

    fun Dispatch.skillNvRouDao() {
        s(30)
        runRight(60, 20L)
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                keepDownKey(100L, A)
                sIf(100.r)
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

    fun Dispatch.skillNvDaQiang() {
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(100.r)
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
            }
        }
    }

    fun Dispatch.skillNvManYou() {
        while (YellowDragonApp.isBind.get()) {
//            runRight(50)
//            this keyPress H
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf S
                this keyPressIf X
                this keyPressIf X
                if (needBreak) break
                this.keepDownKey(100L, A)
                s(50)
                this.keepDownKey(100L, A)
                this keyPressIf X
                this keyPressIf X
                if (needBreak) break
                this keyPressIf D
                sIf(50.r)
                if (needBreak) break
                this keyPressIf F
                this keyPressIf X
                this keyPressIf X
                if (needBreak) break
                this keyPressIf G
                this keyPressIf X
                this keyPressIf X
            }
        }
    }

    fun Dispatch.skillJianMo() {
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                runRight(300)
                sIf(100.r)
                if (needBreak) break
                this keyPressIf S
                runRight(300)
                sIf(100.r)
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

    fun Dispatch.skillHongYan() {
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

    fun Dispatch.skillJianZong() {
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

    fun Dispatch.skillAXiuLuo() {
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

    fun Dispatch.skillBingJieShi() {
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

    fun Dispatch.skillCiYuan() {
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

    fun Dispatch.skillNanSanDa() {
        while (YellowDragonApp.isBind.get()) {
//            if(checkFindResult(this.findPicRepeatedly(3, 699,358,709,369, "痞子张海", offset = 5))){
//                this keyPressIf A
//                sIf(300.r)
//            }
//            if(checkFindResult(this.findPicRepeatedly(3,692,412,708,423, "岳非", offset = 5))){
//                this keyPressIf ALT
//                sIf(20.r)
//            }
            if(findYueFei){
                this keyPressIf A
                s(3000)
            }
            if(findFengSheng){
                this keyPressIf  ALT
                s(100.r)
            }
            if (!YellowDragonApp.needPauseSkills) {
                for (i in 1..8){
                    if (needBreak) break
                    this.keepDownKey(50L, RIGHT, F)
                }
                if (needBreak) break
                this keyPressIf D
                if (needBreak) break
                sIf(300)
                if (needBreak) break
                this keyPressIf G
                if (needBreak) break
                sIf(300)
                if (needBreak) break
                this keyPressIf H
                if (needBreak) break
                sIf(300)
                if (needBreak) break
                this keyPressIf S
                if (needBreak) break
                sIf(300.r)
            }
        }
    }

    fun Dispatch.skillJianHun() {
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
                sIf(100.r)
                if (needBreak) break
                this keyPressIf H
                sIf(100.r)
            }
        }
    }

    fun Dispatch.skillNvQiGong() {
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

    fun Dispatch.skillNanJieBa() {
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

    fun Dispatch.skillCiKe() {
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

    fun Dispatch.skillGuiQi() {
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
                sIf(300)
                if (needBreak) break
                this keyPressIf G
                sIf(300)
            }
        }
    }

    fun Dispatch.skillGuanYu() {
        while (YellowDragonApp.isBind.get()) {
            if (!YellowDragonApp.needPauseSkills) {
                if (needBreak) break
                this keyPressIf A
                sIf(100.r)
                if (needBreak) break
                this keyPressIf S
                sIf(100.r)
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

    fun Dispatch.skillNaiBa() {
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

}