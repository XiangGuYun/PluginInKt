package app.dnf.yd

import base.constant.Key
import base.utils.CommonUtils
import base.utils.DmUtils
import com.jacob.com.Dispatch

interface SkillPresenter: CommonUtils, DmUtils {

    fun Dispatch.common() {
        this.keyPress(Key.a)
        s(300)
        this.keyPress(Key.s)
        s(300)
        this.keyPress(Key.d)
        s(300)
    }

    fun Dispatch.hongYan() {
        this.keyPress(Key.a)
        s(200)
        this.keyPress(Key.a)
        s(300)
        this.keyPress(Key.s)
        s(300)
        this.keyPress(Key.d)
        s(300)
        this.keyPress(Key.f)
        s(300)
        this.keyPress(Key.g)
        s(300)
        this.keyPress(Key.h)
        s(300)
    }

    fun Dispatch.ciKe() {
        this.keyPress(Key.s)
        s(100)
        this.keyPress(Key.s)
        s(100)
        this.keyPress(Key.g)
        s(1000)
    }
    
}