package study

import base.constant.Key
import base.constant.LayoutId
import base.constant.Window
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import javafx.scene.layout.Background
import javafx.scene.layout.BorderStrokeStyle
import sample.base.BaseApp
import kotlin.math.abs

@LayoutId("study_app")
class StudyApp : BaseApp() {
    override fun init(mainWindow: Window) {
        val ta1 = TextField().tip("请输入姓名").textWatcher { ta, oldValue, newValue ->

        }.focus(false)
        val ta2 = TextField().hint("请输入密码").xy(0,60).focus(false)
        ap("parent").addChildren(ta1, ta2)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(StudyApp::class.java)
        }
    }
}