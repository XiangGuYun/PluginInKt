package base.utils.dm

import com.jacob.com.Dispatch

interface PictureColorUtils {
    /**
     * 截图
     */
    fun Dispatch.capture(x1: Int, y1: Int, x2: Int, y2: Int, filePath: String): Boolean {
        return Dispatch.call(this, "Capture", x1, y1, x2, y2, filePath).int == 1
    }

    fun Dispatch.findColor(x1: Int, y1: Int, x2: Int, y2: Int, color: String, sim: Double, dir: Int = 4): String {
        return Dispatch.call(this, "FindColorE", x1, y1, x2, y2, color, sim, dir).string
    }


    fun Dispatch.getColor(x: Int, y: Int): String {
        return Dispatch.call(this, "GetColor", x, y).string
    }

    /*
    0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     */
    enum class DIR {
        LR_TB, LR_BT, RL_TB, RL_BT
    }

    /**
     * 查找指定区域内的图片
     * 这个函数可以查找多个图片,只返回第一个找到的X Y坐标
     * @param x1 整形数:区域的左上X坐标
     * @param y1 整形数:区域的左上Y坐标
     * @param x2 整形数:区域的右下X坐标
     * @param y2 整形数:区域的右下Y坐标
     * @param picName 字符串:图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 字符串:颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @param dir 整形数:查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     */
    fun Dispatch.findPic(x1: Int, y1: Int, x2: Int, y2: Int, name: String, deltaColor: String="101010", sim: Double=0.9, dir: DIR = DIR.LR_TB): String {
        val result = Dispatch.call(this, "FindPicE", x1, y1, x2, y2, "$name.bmp", deltaColor, sim,
                when (dir) {
                    DIR.LR_TB -> 0
                    DIR.LR_BT -> 1
                    DIR.RL_TB -> 2
                    else -> 3
                }).string
        println("==========FIND_PIC:图片\"${name}\"查找结果是$result")
        return result
    }

    /**
     * 函数简介:判断指定的区域，在指定的时间内(秒),图像数据是否一直不变.(卡屏). (或者绑定的窗口不存在也返回1)
     * @param x1 整形数:区域的左上X坐标
     * @param y1 整形数:区域的左上Y坐标
     * @param x2 整形数:区域的右下X坐标
     * @param y2 整形数:区域的右下Y坐标
     * @param t  整形数:需要等待的时间,单位是秒
     * 返回值:整形数:
     * 0 : 没有卡屏，图像数据在变化.
     * 1 : 卡屏. 图像数据在指定的时间内一直没有变化. 或者绑定的窗口不见了.
     * 示例:
     * TracePrint dm.IsDisplayDead(0,0,100,100,5)
     * 注:此函数的原理是不停的截取指定区域的图像，然后比较，如果改变就立刻返回0,否则等待直到指定的时间到达.
     */
    fun Dispatch.IsDisplayDead(x1:Int,y1:Int,x2:Int,y2:Int,t:Int): Boolean {
        return Dispatch.call(this, "IsDisplayDead",x1,y1,x2,y2,t).int == 1
    }

}