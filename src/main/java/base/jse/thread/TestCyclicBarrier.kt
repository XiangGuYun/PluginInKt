package base.jse.thread

import java.util.concurrent.CyclicBarrier

object TestCyclicBarrier {

    @JvmStatic
    fun main(args: Array<String>) {
        var lengthWuGui = 0
        var lengthTuZi = 0
        val cyclicBarrier = CyclicBarrier(2){
            println("乌龟和兔子都跑到了终点")
        }
        val wuGui = Thread{
            while (lengthWuGui<10){
                println("乌龟跑完了${++lengthWuGui}米")
                Thread.sleep(1000)
            }
            cyclicBarrier.await()
        }
        val tuZi = Thread{
            while (lengthTuZi<10){
                lengthTuZi += 2
                println("兔子跑完了${lengthTuZi}米")
                Thread.sleep(1000)
            }
            cyclicBarrier.await()
        }
        wuGui.start()
        tuZi.start()
    }

}