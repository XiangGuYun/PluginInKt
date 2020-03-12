package base.jse.thread

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

/**
 * 本Demo模拟实现如下效果：
 * 在脚本中需要开启三个长期线程来执行各自的任务
 * 线程1任务：检测游戏开头并开始游戏
 * 线程2任务：循环释放技能来打怪
 * 线程3任务：打怪结束后拾取材料金币并再次挑战
 * 现在的需求是任务1执行时，任务2、3的执行处于等待或者阻塞状态，
 * 任务1执行完后，唤醒任务2的执行，并将任务1、3的执行变为等待状态，
 * 任务2执行完后，唤醒任务3的执行，并将任务1、2的执行变为等待状态，依次循环。
 */
class TestCondition {

    companion object {
        private lateinit var t3: Thread
        private lateinit var t2: Thread
        @Volatile
        var currentTag = 1

        @JvmStatic
        fun main(args: Array<String>) {

            val lock = ReentrantLock()
            val cond = lock.newCondition()
            val i = AtomicInteger(1)

            val t1 = Thread {
                while (true) {
                    lock.lock()
                    println("==========线程1获得了执行权==========")
                    if (currentTag == 1) {
                        println("线程1在执行$i " + "t2的状态是${t2.state}" + " t3的状态是${t3.state}")
                        Thread.sleep(1000)
                        i.incrementAndGet()
                        if (i.get() == 10) {
                            currentTag = 2
                            cond.await()
                            cond.signalAll()
                        }
                    }
                    lock.unlock()
                }
            }

            t2 = Thread {
                while (true) {
                    try {
                        lock.lock()
                        println("==========线程2获得了执行权==========")
                        if (currentTag == 2) {
                            println("线程2在执行$i")
                            i.incrementAndGet()
                            Thread.sleep(1000)
                            if (i.get() == 20) {
                                currentTag = 3
                                cond.await()
                                cond.signalAll()
                            }
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        lock.unlock()
                    }
                }
            }


            t3 = Thread {
                while (true) {
                    try {
                        lock.lock()
                        println("==========线程3获得了执行权==========")
                        if (currentTag == 3) {
                            println("线程3在执行$i "+ "t1的状态是${t2.state}" + " t2的状态是${t3.state}")
                            i.incrementAndGet()
                            Thread.sleep(1000)
                            if (i.get() == 30) {
                                currentTag = 1
                                i.set(1)
//                                cond.await() //这句代码不能添加，不能让所有线程都处于等待状态
                                cond.signalAll()
                                cond.await()
                            }
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            t1.start()
            t2.start()
            t3.start()
        }
    }

}