package base.jse.thread

import base.constant.AppIcon
import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import sample.base.BaseApp
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 如果业务需要多次new Thread()，请使用线程池来代替执行，来更好的管理线程的执行和开销。
 */
@AppIcon("java.png")
@AppTitle("ThreadPoolApp")
class ThreadPoolApp : BaseApp(){

    override fun init(window: Window) {

        val es = Executors.newFixedThreadPool(3)

        window.scene = Scene(VBox().addChildren(
                Button("Fixed").click {
                   es.submit {
                       while (true){
                           "正在扫地...".pln(Thread.currentThread().name)
                           Thread.sleep(1000)
                       }
                   }
                    es.submit {
                        while (true){
                            "正在烧饭...".pln(Thread.currentThread().name)
                            Thread.sleep(3000)
                        }
                    }
                    es.submit {
                        while (true){
                            "正在睡觉...".pln(Thread.currentThread().name)
                            Thread.sleep(5000)
                        }
                    }
                },
                Button("关闭线程池").click {
                   es.shutdownNow()
                }
        ).preSize(500, 500).apply {
            spacing = 20.0
            alignment = Pos.CENTER
        })
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ThreadPoolApp::class.java)
        }
    }

}