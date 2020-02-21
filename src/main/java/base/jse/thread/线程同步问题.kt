package base.jse.thread

import base.constant.AppIcon
import base.constant.AppTitle
import base.constant.Window
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox
import sample.base.BaseApp
import java.util.concurrent.locks.ReentrantLock

@AppIcon("java.png")
@AppTitle("ThreadSyncApp")
class ThreadSyncApp : BaseApp() {

    override fun init(window: Window) {
        /*
        同步代码块中的锁对象可以是任何类型的对象
        多线程使用的锁对象必须是同一个
        锁对象作用：只让拿到锁的线程执行同步代码块，避免同时访问
         */

        //售票
        val sellTickets = Runnable {
            while (tickets > 0) {
                Thread.sleep(100)
                "线程${Thread.currentThread().name}==>正在卖第${tickets}张票".pln()
                tickets--
            }
        }

        //售票（同步代码块）
        val any = Any()
        val sellTickets1 = Runnable {
            while (tickets > 0) {
                synchronized(any) {
                    Thread.sleep(100)
                    "线程${Thread.currentThread().name}==>正在卖第${tickets}张票".pln()
                    tickets--
                }
            }
        }

        /**
         * Kotlin中没有Synchronized关键字，可以通过@Synchronized将其转换为同步方法
         * 同步方法中锁对象是执行该方法的对象，即Runnable对象
         */
        @Synchronized
        fun sellTicket2() {
            while (tickets > 0) {
                Thread.sleep(100)
                "线程${Thread.currentThread().name}==>正在卖第${tickets}张票".pln()
                tickets--
            }
        }

        val sellTicket4 = Runnable {
            val lock = ReentrantLock()
            while (tickets > 0) {
                lock.lock()
                Thread.sleep(100)
                "线程${Thread.currentThread().name}==>正在卖第${tickets}张票".pln()
                tickets--
                lock.unlock()
            }
        }

        window.scene = Scene(VBox().addChildren(
                Button("卖票").click {
                    (1..3).toList().map {
                        Thread(sellTickets).apply { name = "T$it" }
                    }.forEach { it.start() }
                },
                Button("卖票(同步代码块)").click {
                    (1..3).toList().map {
                        Thread(sellTickets1).apply { name = "T$it" }
                    }.forEach { it.start() }
                },
                Button("卖票(同步方法)").click {
                    (1..3).toList().map {
                        Thread {
                            sellTicket2()
                        }.apply { name = "T$it" }
                    }.forEach { it.start() }
                },
                Button("卖票(静态同步方法)").click {
                    (1..3).toList().map {
                        Thread {
                            sellTickets3()
                        }.apply { name = "T$it" }
                    }.forEach { it.start() }
                },
                Button("卖票(Lock)").click {
                    (1..3).toList().map {
                        Thread {
                            sellTickets3()
                        }.apply { name = "T$it" }
                    }.forEach { it.start() }
                }
        ).preSize(500, 500).apply {
            spacing = 20.0
            alignment = Pos.CENTER
        })
    }

    companion object {

        var tickets = 100 //票数

        @JvmStatic
        fun main(args: Array<String>) {
            launch(ThreadSyncApp::class.java)
        }

        /**
         * 静态同步方法拿到的锁是本类的class属性
         */
        @Synchronized
        fun sellTickets3(){
            while (tickets > 0) {
                Thread.sleep(100)
                println("线程${Thread.currentThread().name}==>正在卖第${tickets}张票")
                tickets--
            }
        }

    }

}