package base.jse.thread

import base.constant.JO
import java.util.concurrent.atomic.AtomicInteger

class TestWaitNotify {

    companion object{
        private lateinit var t2: Thread
        val obj = JO()
        @JvmStatic
        fun main(args: Array<String>) {

            val i = AtomicInteger(1)
            val t1 = Thread {
                while (true) {
                    synchronized(obj) {
                        println("线程1在执行$i "+"t2的状态是${t2.state}")
                        Thread.sleep(2000)
                        i.incrementAndGet()
                        if(i.get()==3){
                            obj.wait()
                        }
                    }
                }
            }

            t2 = Thread {
                while (true) {
                    synchronized(obj) {
                        println("线程2在执行$i "+"t1的状态是${t1.state}")
                        i.incrementAndGet()
                        Thread.sleep(2000)
                        if(i.get()>=9){
                            obj.notifyAll()
                            obj.wait()
                        }
                    }
                }
            }

            val t3 = Thread {
                while (true) {
                    synchronized(obj) {
                        println("线程3在执行$i")
                        Thread.sleep(2000)
                        i.incrementAndGet()
                        if(i.get()==6||i.get()==12){
                            obj.wait()
                            obj.notifyAll()
                        }
                    }
                }
            }

            t1.start()
            t2.start()
//            t3.start()

        }
    }

}