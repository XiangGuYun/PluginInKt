package test

import base.constant.Window
import base.utils.DllUtils
import com.sun.jna.*
import javafx.application.Application
import javafx.stage.Stage
import sample.base.BaseApp
import java.io.File
import java.lang.reflect.Method
import java.util.*

class TestDll : BaseApp(), DllUtils {
    /**
     * DLL动态库调用方法
     * @Description: 读取调用CDecl方式导出的DLL动态库方法
     * @author: LinWenLi
     * @date: 2018年7月18日 上午10:49:02
     */
    interface CLibrary : Library {
        companion object : DllUtils{
            val INSTANCE get() =  Native.load(System.getenv()["JAVA_HOME"] +"/jre/bin/test", CLibrary::class.java, getOptions(mapOf("AddFunction" to "?AddFunction@@YAHHH@Z"))) as CLibrary
        }
        fun AddFunction(a: Int, b: Int): Int
        fun sub(a: Int, b: Int): Int
    }

    override fun init(window: Window) {
        window.apply {
            width = 500.0
            height = 200.0
            title = System.getenv()["JAVA_HOME"]+"  "+CLibrary.INSTANCE.AddFunction(6, 6).toString()
        }.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(TestDll::class.java)
        }
    }
}