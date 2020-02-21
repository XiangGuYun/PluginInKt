package base.jse.thread

import base.constant.JO

data class BaoZi(var pi: String = "", var xian: String = "", var flag: Boolean = false) : JO()

class BaoZiPu(val bz: BaoZi) : Thread() {
    var count = 0
    override fun run() {
        synchronized(bz) {
            while (true) {
                if (bz.flag) {
                    println("包子铺进入等待状态...")
                    bz.wait() //如果有包子则包子铺进入等待状态
                }
                if (count % 2 == 0) {
                    //生成薄皮
                    bz.pi = "薄皮"
                    bz.xian = "三鲜馅"
                } else {
                    //生产冰皮
                    bz.pi = "冰皮"
                    bz.xian = "芹菜陷"
                }
                count++
                println("包子铺正在生产${bz.pi + bz.xian}包子")
                //生产包子需要三秒钟
                sleep(3000)
                bz.flag = true
                println("包子铺已经生产好了：${bz.pi + bz.xian}包子")
            }
        }
    }
}

class ChiHuo(val bz: BaoZi) : Thread() {
    override fun run() {
        while (true) {
            synchronized(bz) {
                if (!bz.flag) {
                    bz.wait()
                }
            }
            println("吃货正在吃${bz.pi + bz.xian}包子")
            bz.flag = false
            bz.notify()
        }
    }
}

class Demo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val bz = BaoZi()
            BaoZiPu(bz).start()
            ChiHuo(bz).start()
        }
    }
}