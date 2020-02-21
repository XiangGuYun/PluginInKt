package base.jse.thread

import base.constant.AppIcon
import base.constant.AppTitle
import base.constant.JO
import base.constant.Window
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import sample.base.BaseApp

/**
 * 案例介绍
 * 顾客线程和老板线程必须用同步代码块包裹起来，保证等待和唤醒不能同时执行
 * 同步使用的锁对象必须保证唯一性，且只要锁对象才能调用wait和notify
 */
@AppIcon("java.png")
@AppTitle("ThreadWaitNotifyApp")
class ThreadWaitNotifyApp : BaseApp(){

    override fun init(window: Window) {
        //锁对象
        val lock = JO()
        //顾客线程
        val threadBuyer = Thread{
            synchronized(lock){
//              while (true){
                  "小明买包子".pln()
                  lock.wait()
//                  lock.wait(3000) //等待超出3秒后即使没有被唤醒也会自动唤醒
                  //唤醒之后执行的代码
                  "小明吃到了包子".pln()
//              }
            }
        }
        //顾客线程1
        val threadBuyer1 = Thread{
            synchronized(lock){
//                while (true){
                    "小吴买包子".pln()
                    lock.wait()
                    //唤醒之后执行的代码
                    "小吴吃到了包子".pln()
//                }
            }
        }
        //老板线程
        val threadBoss = Thread{
            while (true){
                Thread.sleep(5000)
                synchronized(lock){
                    "包子做好了".pln()
                    lock.notify()
                }
            }
        }
        //老板线程1
        val threadBoss1 = Thread{
            while (true){
                Thread.sleep(5000)
                synchronized(lock){
                    "包子做好了".pln()
                    lock.notifyAll()
                }
            }
        }

        window.scene = Scene(VBox().addChildren(
                Button("案例1：唤醒单个").click {
                    threadBuyer.start()
                    threadBoss.start()
                }.apply { padding = Insets(5.0) },
                Button("案例2：唤醒全部").click {
                    threadBuyer.start()
                    threadBuyer1.start()
                    threadBoss1.start()
                }.apply { padding = Insets(5.0) }
        ).apply {
            padding = Insets(20.0)
            spacing = 20.0
            alignment = Pos.CENTER
            preSize(300,300)
        })

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(ThreadWaitNotifyApp::class.java)
        }
    }

}