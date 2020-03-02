package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 大漠图色模块
 */
interface PictureColorUtils {

    /**
     * 函数简介: 抓取指定区域(x1, y1, x2, y2)的图像,保存为file(24位位图)
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param file String 保存的文件名,保存的地方一般为SetPath中设置的目录
     * 当然这里也可以指定全路径名
     */
    fun Dispatch.capture(x1: Int, y1: Int, x2: Int, y2: Int, file: String): Boolean {
        return Dispatch.call(this, "Capture", x1, y1, x2, y2, file).int == 1
    }

    /**
     * 函数简介: 抓取指定区域(x1, y1, x2, y2)的动画，保存为gif格式
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param file String 保存的文件名，保存的地方一般为SetPath中设置的目录
     * 当然这里也可以指定全路径名
     * @param delay Int 动画间隔，单位毫秒。如果为0，表示只截取静态图片
     * @param time Int 总共截取多久的动画，单位毫秒
     */
    fun Dispatch.captureGif(x1: Int, y1: Int, x2: Int, y2: Int, file: String, delay: Int, time: Int): Boolean {
        return Dispatch.call(this, "CaptureGif", x1, y1, x2, y2, file, delay, time).int == 1
    }

    /**
     * 函数简介: 抓取指定区域(x1, y1, x2, y2)的图像，保存为file(JPG压缩格式)
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param file String 保存的文件名,保存的地方一般为SetPath中设置的目录
     * 当然这里也可以指定全路径名
     * @param quality Int jpg压缩比率(1-100)，越大图片质量越好
     */
    fun Dispatch.captureJpg(x1: Int, y1: Int, x2: Int, y2: Int, file: String, quality: Int): Boolean {
        return Dispatch.call(this, "CaptureJpg", x1, y1, x2, y2, file, quality).int == 1
    }

    /**
     * 函数简介: 同Capture函数，只是保存的格式为PNG
     */
    fun Dispatch.capturePng(x1: Int, y1: Int, x2: Int, y2: Int, file: String): Boolean {
        return Dispatch.call(this, "CapturePng", x1, y1, x2, y2, file).int == 1
    }

    /**
     * 函数简介: 抓取上次操作的图色区域，保存为file(24位位图)
     * @param file String 保存的文件名,保存的地方一般为SetPath中设置的目录
     * 当然这里也可以指定全路径名
     * 注意，要开启此函数，必须先调用EnableDisplayDebug
     * 任何图色或者文字识别函数，都可以通过这个来截取
     * 具体可以查看常见问题中 "本机文字识别正常，别的机器为何不正常" 这一节
     */
    fun Dispatch.capturePre(file: String): Boolean {
        return Dispatch.call(this, "CapturePng", file).int == 1
    }

    /**
     * 函数简介: 比较指定坐标点(x,y)的颜色
     * @param x Int X坐标
     * @param y Int Y坐标
     * @param color String 颜色字符串,可以支持偏色,多色,
     * 例如 "ffffff-202020|000000-000000"
     * 这个表示白色偏色为202020,和黑色偏色为000000
     * 颜色最多支持10种颜色组合. 注意，这里只支持RGB颜色
     * @param sim Double  相似度(0.1-1.0)
     * @return true表示匹配
     */
    fun Dispatch.cmpColor(x: Int, y: Int, color: String, sim: Double): Boolean {
        return Dispatch.call(this, "CmpColor", x, y, color, sim).int == 1
    }

    /**
     * 函数简介: 开启图色调试模式，此模式会稍许降低图色和文字识别的速度，默认不开启
     * @return true表示开启成功
     */
    fun Dispatch.enableDisplayDebug(enable: Boolean): Boolean {
        return Dispatch.call(this, "EnableDisplayDebug", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 当执行FindPicXXX系列接口时，是否在条件满足下(查找的图片大于等于4)，开启多线程查找，默认打开
     * @return true表示开启成功
     * 注 : 如果担心开启多线程会引发占用大量CPU资源,那么可以考虑关闭此功能
     * 在以往版本,这个功能默认都是打开的
     */
    fun Dispatch.enableFindPicMultiThread(enable: Boolean): Boolean {
        return Dispatch.call(this, "EnableFindPicMultithread", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 允许调用GetColor、GetColorBGR、GetColorHSV以及CmpColor时，以截图的方式来获取颜色，默认关闭
     * @return true表示开启成功
     * 注:某些窗口上，可能GetColor会获取不到颜色，可以尝试此接口
     */
    fun Dispatch.enableGetColorByCapture(enable: Boolean): Boolean {
        return Dispatch.call(this, "EnableGetColorByCapture", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 查找指定区域内的颜色,颜色格式 "RRGGBB-DRDGDB"，注意和按键的颜色格式相反
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param color String 颜色 格式为 "RRGGBB-DRDGDB"
     * 比如"123456-000000|aabbcc-202020"，注意这里只支持RGB颜色
     * @param sim Double 相似度，取值范围为0.1-1.0
     * @param dir Dir 查找方向
     * 0: 从左到右，从上到下
     * 1: 从左到右，从下到上
     * 2: 从右到左，从上到下
     * 3: 从右到左，从下到上
     * 4: 从中心往外查找
     * 5: 从上到下，从左到右
     * 6: 从上到下，从右到左
     * 7: 从下到上，从左到右
     * 8: 从下到上，从右到左
     * @return String 返回X和Y坐标，形式如"x|y"，比如"100|200"
     */
    fun Dispatch.findColor(x1: Int, y1: Int, x2: Int, y2: Int, color: String, sim: Double, dir: Int = 4): String {
        return Dispatch.call(this, "FindColorE", x1, y1, x2, y2, color, sim, dir).string
    }

    /**
     * 函数简介: 查找指定区域内的所有颜色块,颜色格式 "RRGGBB-DRDGDB" ，注意和按键的颜色格式相反
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param color String 颜色，格式为 "RRGGBB-DRDGDB"
     * 比如"aabbcc-000000|123456-202020"，注意这里只支持RGB颜色
     * @param sim Double 相似度,取值范围0.1-1.0
     * @param count Int 在宽度为width,高度为height的颜色块中，符合color颜色的最小数量
     * 注意，这个颜色数量可以在综合工具的二值化区域中看到
     * @param width Int 颜色块的宽度
     * @param height Int 颜色块的高度
     * @return String 返回所有颜色块信息的坐标值,然后通过GetResultCount等接口来解析
     * 由于内存限制,返回的颜色数量最多为1800个左右
     */
    fun Dispatch.findColorBlock(x1: Int, y1: Int, x2: Int, y2: Int, color: String, sim: Double, count: Int,
                                width: Int, height: Int): String {
        return Dispatch.call(this, "FindColorBlockEx", x1, y1, x2, y2, color, sim, count,
                width, height).string
    }

    /**
     * 函数简介: 查找指定区域内的所有颜色
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param color String 颜色，格式为 "RRGGBB-DRDGDB" ，比如 "123456-000000|aabbcc-202020" ，注意这里只支持RGB颜色
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @return Int
     * false:没找到或者部分颜色没找到
     * true:所有颜色都找到
     */
    fun Dispatch.findMulColor(x1: Int, y1: Int, x2: Int, y2: Int, color: String, sim: Double): Boolean {
        return Dispatch.call(this, "FindMulColor", x1, y1, x2, y2, color, sim).int == 1
    }

    /**
     * 函数简介: 根据指定的多点查找颜色坐标
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param first_color String 颜色 格式为 "RRGGBB-DRDGDB|RRGGBB-DRDGDB|…………" ,比如 "123456-000000"
     * 这里的含义和按键自带Color插件的意义相同，只不过我的可以支持偏色和多种颜色组合
     * 所有的偏移色坐标都相对于此颜色.注意，这里只支持RGB颜色.
     * @param offset_color String 偏移颜色，可以支持任意多个点，格式和按键自带的Color插件意义相同，只不过我的可以支持偏色和多种颜色组合
     * 格式为 "x1|y1|RRGGBB-DRDGDB|RRGGBB-DRDGDB……,……xn|yn|RRGGBB-DRDGDB|RRGGBB-DRDGDB……"
     * 比如 "1|3|aabbcc|aaffaa-101010,-5|-3|123456-000000|454545-303030|565656" 等任意组合都可以，支持偏色
     * 还可以支持反色模式，比如 "1|3|-aabbcc|-334455-101010,-5|-3|-123456-000000|-353535|454545-101010" ， "-" 表示除了指定颜色之外的颜色
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @param dir Dir 查找方向
     * 0: 从左到右，从上到下
     * 1: 从左到右，从下到上
     * 2: 从右到左，从上到下
     * 3: 从右到左，从下到上
     * @return String 返回X和Y坐标 形式如 "x|y" ，比如 "100|200"
     */
    fun Dispatch.findMultiColor(x1: Int, y1: Int, x2: Int, y2: Int, first_color: String, offset_color: String,
                                sim: Double, dir: Int): String {
        return Dispatch.call(this, "FindMultiColorE", x1, y1, x2, y2, first_color, offset_color,
                sim, dir).string
    }

    /**
     * 函数简介: 根据指定的多点查找所有颜色坐标
     * @return String
     * 返回所有颜色信息的坐标值，然后通过GetResultCount等接口来解析
     * (由于内存限制,返回的坐标数量最多为1800个左右)，坐标是first_color所在的坐标
     */
    fun Dispatch.findMultiColorEx(x1: Int, y1: Int, x2: Int, y2: Int, first_color: String, offset_color: String,
                                  sim: Double, dir: Int): String {
        return Dispatch.call(this, "FindMultiColorEx", x1, y1, x2, y2, first_color, offset_color,
                sim, dir).string
    }

    enum class DIR {
        LR_TB, //0: 从左到右,从上到下
        LR_BT, //1: 从左到右,从下到上
        RL_TB, //2: 从右到左,从上到下
        RL_BT //从右到左, 从下到上
    }

    /**
     * 函数简介: 查找指定区域内的图片，这个函数可以查找多个图片，只返回第一个找到的X和Y坐标
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param name String 图片名，可以是多个图片，比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor String 颜色色偏比如 "203040" ，表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim Double 相似度，取值范围0.1-1.0
     * @param dir Dir 查找方向
     * LR_TB 0: 从左到右，从上到下
     * LR_BT 1: 从左到右，从下到上
     * RL_TB 2: 从右到左，从上到下
     * RL_BT 3: 从右到左，从下到上
     * @return String 返回找到的图片序号(从0开始索引)以及X和Y坐标，形式如 "index|x|y" ，比如 "3|100|200"
     */
    fun Dispatch.findPic(x1: Int, y1: Int, x2: Int, y2: Int, name: String, deltaColor: String = "101010", sim: Double = 0.9, dir: DIR = DIR.LR_TB): String {
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
     * 函数简介: 查找指定区域内的图片，位图必须是24位色格式，支持透明色
     * 当图像上下左右4个顶点的颜色一样时，则这个颜色将作为透明色处理
     * 这个函数可以查找多个图片，只返回第一个找到的X Y坐标
     * 此函数同FindPic，只是返回值不同
     * @return String 返回找到的图片的文件名，没找到则返回空字符串
     */
    fun Dispatch.findPicS(x1: Int, y1: Int, x2: Int, y2: Int, name: String, deltaColor: String = "101010", sim: Double = 0.9, dir: DIR = DIR.LR_TB): String {
        val result = Dispatch.call(this, "findPicS", x1, y1, x2, y2, "$name.bmp", deltaColor, sim,
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
     * 函数简介: 查找指定区域内的图片，位图必须是24位色格式，支持透明色
     * 当图像上下左右4个顶点的颜色一样时，则这个颜色将作为透明色处理
     * 这个函数可以查找多个图片，并且返回所有找到的图像的坐标
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param name String 图片名，可以是多个图片，比如 "test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor String 颜色色偏比如 "203040" ，表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * 如果这里的色偏是2位，表示使用灰度找图，比如"20"
     * @param sim Double 相似度，取值范围0.1-1.0
     * @param dir Dir 查找方向
     * LR_TB 0: 从左到右，从上到下
     * LR_BT 1: 从左到右，从下到上
     * RL_TB 2: 从右到左，从上到下
     * RL_BT 3: 从右到左，从下到上
     * @return String
     * 返回的是所有找到的坐标格式如下: "id,x,y|id,x,y..|id,x,y" (图片左上角的坐标)
     * 比如 "0,100,20|2,30,40" 表示找到了两个
     * 第一个对应的图片是图像序号为0的图片且坐标是(100,20)
     * 第二个对应的图片是图像序号为2的图片且坐标是(30,40)
     * 由于内存限制,返回的图片数量最多为1500个左右
     */
    fun Dispatch.findPicEx(x1: Int, y1: Int, x2: Int, y2: Int, name: String, deltaColor: String = "101010", sim: Double = 0.9, dir: DIR = DIR.LR_TB): String {
        val result = Dispatch.call(this, "FindPicEx", x1, y1, x2, y2, "$name.bmp", deltaColor, sim,
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
     * 函数简介: 查找指定区域内的图片，位图必须是24位色格式，支持透明色
     * 当图像上下左右4个顶点的颜色一样时，则这个颜色将作为透明色处理
     * 这个函数可以查找多个图片，并且返回所有找到的图像的坐标
     * 此函数同FindPicEx，只是返回值不同
     * @return String
     * 返回的是所有找到的坐标格式如下:"file,x,y| file,x,y..| file,x,y" (图片左上角的坐标)
     * 比如 "1.bmp,100,20|2.bmp,30,40" 表示找到了两个
     * 第一个对应的图片是1.bmp且坐标是(100,20)
     * 第二个对应的图片是2.bmp且坐标是(30,40)
     * 由于内存限制,返回的图片数量最多为1500个左右
     */
    fun Dispatch.findPicExS(x1: Int, y1: Int, x2: Int, y2: Int, name: String, deltaColor: String = "101010", sim: Double = 0.9, dir: DIR = DIR.LR_TB): String {
        val result = Dispatch.call(this, "FindPicExS", x1, y1, x2, y2, "$name.bmp", deltaColor, sim,
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
     * 函数简介: 查找指定的形状，形状的描述同按键的抓抓，具体可以参考按键的抓抓
     * 和按键的语法不同，需要用大漠综合工具的颜色转换
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param offset_color String 坐标偏移描述，可以支持任意多个点，
     * 格式和按键自带的Color插件意义相同，格式为 "x1|y1|e1,……xn|yn|en" ，比如"1|3|1,-5|-3|0"等任意组合都可以
     * @param sim Double 相似度，取值范围0.1-1.0
     * @param dir Dir 查找方向
     * LR_TB 0: 从左到右，从上到下
     * LR_BT 1: 从左到右，从下到上
     * RL_TB 2: 从右到左，从上到下
     * RL_BT 3: 从右到左，从下到上
     * @return String 返回X和Y坐标 形式如 "x|y" , 比如 "100|200"
     */
    fun Dispatch.findShape(x1: Int, y1: Int, x2: Int, y2: Int, offset_color: String, sim: Double = 0.9, dir: DIR = DIR.LR_TB): String {
        return Dispatch.call(this, "FindShapeE", x1, y1, x2, y2, offset_color, sim,
                when (dir) {
                    DIR.LR_TB -> 0
                    DIR.LR_BT -> 1
                    DIR.RL_TB -> 2
                    else -> 3
                }).string
    }

    /**
     * 函数简介: 查找所有指定的形状的坐标，形状的描述同按键的抓抓，具体可以参考按键的抓抓
     * 和按键的语法不同，需要用大漠综合工具的颜色转换
     * @return String 返回所有形状的坐标值,然后通过GetResultCount等接口来解析(由于内存限制,返回的坐标数量最多为1800个左右)
     */
    fun Dispatch.findShapeEx(x1: Int, y1: Int, x2: Int, y2: Int, offset_color: String, sim: Double = 0.9, dir: DIR = DIR.LR_TB): String {
        val result = Dispatch.call(this, "FindShapeEx", x1, y1, x2, y2, offset_color, sim,
                when (dir) {
                    DIR.LR_TB -> 0
                    DIR.LR_BT -> 1
                    DIR.RL_TB -> 2
                    else -> 3
                }).string
        return result
    }

    /**
     * 函数简介: 释放指定的图片，此函数不必要调用，除非你想节省内存
     * @param pic_name String 文件名比如 "1.bmp|2.bmp|3.bmp" 等，可以使用通配符，比如 "*.bmp" 对应了所有的bmp文件
     * "a?c*.bmp" 代表了所有第一个字母是a，第三个字母是c，第二个字母任意的所有bmp文件
     * 也可以这样任意组合，"abc???.bmp|1.bmp|aa??.bmp"
     * @return Boolean true表示成功
     */
    fun Dispatch.freePic(pic_name: String): Boolean {
        return Dispatch.call(this, "FreePic", pic_name).int == 1
    }

    /**
     * 函数简介: 获取范围(x1,y1,x2,y2)颜色的均值，返回格式 "H.S.V"
     * @param x1 Int 左上角X
     * @param y1 Int 左上角Y
     * @param x2 Int 右下角X
     * @param y2 Int 右下角Y
     * @return String 颜色字符串
     */
    fun Dispatch.getAveHSV(x1: Int, y1: Int, x2: Int, y2: Int): String {
        return Dispatch.call(this, "GetAveHSV", x1, y1, x2, y2).string
    }

    /**
     * 函数简介: 获取范围(x1,y1,x2,y2)颜色的均值，返回格式 "RRGGBB"
     * @param x1 Int 左上角X
     * @param y1 Int 左上角Y
     * @param x2 Int 右下角X
     * @param y2 Int 右下角Y
     * @return String 颜色字符串
     */
    fun Dispatch.getAveRGB(x1: Int, y1: Int, x2: Int, y2: Int): String {
        return Dispatch.call(this, "GetAveRGB", x1, y1, x2, y2).string
    }

    /**
     * 函数简介: 获取(x,y)的颜色,颜色返回格式 "RRGGBB" ，注意和按键的颜色格式相反
     * @param x Int X坐标
     * @param y Int Y坐标
     * @return String 颜色字符串(注意这里都是小写字符，和工具相匹配)
     */
    fun Dispatch.getColor(x: Int, y: Int): String {
        return Dispatch.call(this, "GetColor", x, y).string
    }

    /**
     * 函数简介: 获取(x,y)的颜色,颜色返回格式 "BBGGRR"
     * @param x Int X坐标
     * @param y Int Y坐标
     * @return String 颜色字符串(注意这里都是小写字符，和工具相匹配)
     */
    fun Dispatch.getColorBGR(x: Int, y: Int): String {
        return Dispatch.call(this, "GetColorBGR", x, y).string
    }

    /**
     * 函数简介: 获取(x,y)的颜色,颜色返回格式 "H.S.V"
     * @param x Int X坐标
     * @param y Int Y坐标
     * @return String 颜色字符串(注意这里都是小写字符，和工具相匹配)
     */
    fun Dispatch.getColorHSV(x: Int, y: Int): String {
        return Dispatch.call(this, "GetColorHSV", x, y).string
    }

    /**
     * 函数简介: 获取指定区域的颜色数量，颜色格式 "RRGGBB-DRDGDB" ，注意和按键的颜色格式相反
     * @param x1 Int 左上角X
     * @param y1 Int 左上角Y
     * @param x2 Int 右下角X
     * @param y2 Int 右下角Y
     * @param color String 颜色，格式为 "RRGGBB-DRDGDB"
     * 比如 "123456-000000|aabbcc-202020" ，注意这里只支持RGB颜色
     * @param sim Double 相似度，取值范围0.1-1.0
     * @return Int 颜色数量
     */
    fun Dispatch.getColorNum(x1: Int, y1: Int, x2: Int, y2: Int, color: String, sim: Double): Int {
        return Dispatch.call(this, "GetColorNum", x1, y1, x2, y2).int
    }

    /**
     * 函数简介: 获取指定图片的尺寸，如果指定的图片已经被加入缓存，则从缓存中获取信息.
     * 此接口也会把此图片加入缓存(当图色缓存机制打开时，具体参考EnablePicCache)
     * @param pic_name String 文件名比如 "1.bmp"
     * @return String 形式如 "w,h" 比如 "30,20"
     */
    fun Dispatch.getPicSize(pic_name: String): Boolean {
        return Dispatch.call(this, "GetPicSize", pic_name).int == 1
    }

    /**
     * 函数简介: 获取指定区域的图像，用二进制数据的方式返回,（不适合按键使用）方便二次开发
     * @param x1 Int 左上角X
     * @param y1 Int 左上角Y
     * @param x2 Int 右下角X
     * @param y2 Int 右下角Y
     * @return Int 返回的是指定区域的二进制颜色数据地址，每个颜色是4个字节，表示方式为(00RRGGBB)
     */
    fun Dispatch.getScreenData(x1: Int, y1: Int, x2: Int, y2: Int): String {
        return Dispatch.call(this, "GetScreenData", x1, y1, x2, y2).string
    }

    /**
     * 函数简介: 转换图片格式为24位BMP格式
     * @param pic_name String 要转换的图片名
     * @param bmp_name String 要保存的BMP图片名
     * @return Boolean true表示成功
     */
    fun Dispatch.imageToBmp(pic_name: String, bmp_name: String): Boolean {
        return Dispatch.call(this, "ImageToBmp", pic_name).int == 1
    }

    /**
     * 函数简介: 判断指定的区域，在指定的时间内(秒)，图像数据是否一直不变(卡屏或者绑定的窗口不存在)
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param t  Int 需要等待的时间,单位是秒
     * @return Boolean
     * false: 没有卡屏，图像数据在变化
     * true: 卡屏，图像数据在指定的时间内一直没有变化，或者绑定的窗口不见了
     * 注:此函数的原理是不停的截取指定区域的图像，然后比较，如果改变就立刻返回0，否则等待直到指定的时间到达
     */
    fun Dispatch.isDisplayDead(x1: Int, y1: Int, x2: Int, y2: Int, t: Int): Boolean {
        return Dispatch.call(this, "IsDisplayDead", x1, y1, x2, y2, t).int == 1
    }

    /**
     * 函数简介: 预先加载指定的图片，这样在操作任何和图片相关的函数时，将省去了加载图片的时间
     * 调用此函数后，没必要一定要调用FreePic，插件自己会自动释放
     * 另外，此函数不是必须调用的，所有和图形相关的函数只要调用过一次，图片会自动加入缓存
     * 如果想对一个已经加入缓存的图片进行修改，那么必须先用FreePic释放此图片在缓存中占用的内存
     * 然后重新调用图片相关接口，就可以重新加载此图片(当图色缓存机制打开时,具体参考EnablePicCache)
     * @param pic_name String 文件名比如"1.bmp|2.bmp|3.bmp" 等,可以使用通配符,比如 "*.bmp" 这个对应了所有的bmp文件
     * "a?c*.bmp" 这个代表了所有第一个字母是a，第三个字母是c，第二个字母任意的所有bmp文件
     * 也可以这样任意组合，"abc???.bmp|1.bmp|aa??.bmp"
     * @return Boolean true表示成功
     */
    fun Dispatch.preLoadPic(pic_name: String): Boolean {
        return Dispatch.call(this, "LoadPic", pic_name).int == 1
    }

    /**
     * 函数简介: 此函数同LoadPic，只不过LoadPic是从文件中加载图片，而LoadPicByte从给定的内存中加载
     * @param address Int BMP图像首地址(完整的BMP图像，不是经过解析的，和BMP文件里的内容一致)
     * @param size Int BMP图像大小(和BMP文件大小一致)
     * @param pic_name String 文件名，指定这个地址对应的图片名，用于找图时使用
     * @return Boolean true表示成功
     */
    fun Dispatch.preLoadPicByte(address: Int, size: Int, pic_name: String): Boolean {
        return Dispatch.call(this, "LoadPicByte", address, size, pic_name).int == 1
    }

    /**
     * 函数简介: 根据通配符获取文件集合. 方便用于FindPic和FindPicEx
     * @param pic_name String 文件名比如 "1.bmp|2.bmp|3.bmp" 等，可以使用通配符，比如 "*.bmp" 对应了所有的bmp文件
     * "a?c*.bmp" 代表了所有第一个字母是a，第三个字母是c，第二个字母任意的所有bmp文件
     * 也可以这样任意组合，"abc???.bmp|1.bmp|aa??.bmp"
     * @return String 返回的是通配符对应的文件集合，每个图片以|分割
     * 示例:
     * PutAttachment "c:\test", "*.bmp"
     * dm_ret = dm.SetPath("c:\test")
     * all_pic = "abc*.bmp"
     * pic_name = dm.MatchPicName(all_pic)
     * 比如c:\test目录下有abc001.bmp、abc002.bmp、abc003.bmp
     * 那么pic_name的值为abc001.bmp|abc002.bmp|abc003.bmp
     */
    fun Dispatch.matchPicName(pic_name: String): String {
        return Dispatch.call(this, "MatchPicName", pic_name).string
    }

    /**
     * 函数简介: 把RGB的颜色格式转换为BGR(按键格式)
     * @param rgb_color String rgb格式的颜色字符串
     * @return String BGR格式的字符串
     */
    fun Dispatch.rgb2bgr(rgb_color: String): String {
        return Dispatch.call(this, "RGB2BGR", rgb_color).string
    }

    /**
     * 函数简介: 设置图色,以及文字识别时,需要排除的区域(支持所有图色接口，以及文字相关接口，但对单点取色或者单点颜色比较的接口不支持)
     * @param mode Int 模式,取值如下:
     * 0: 添加排除区域
     * 1: 设置排除区域的颜色，默认颜色是FF00FF(此接口的原理是把排除区域设置成此颜色,这样就可以让这块区域实效)
     * 2: 请空排除区域
     * @param info String 根据mode的取值来决定
     * 当mode为0时，此参数指添加的区域，可以多个区域，用 "|" 相连，格式为 "x1,y1,x2,y2|....."
     * 当mode为1时，此参数为排除区域的颜色，"RRGGBB"
     * 当mode为2时，此参数无效
     * @return Boolean true表示成功
     */
    fun Dispatch.setExcludeRegion(mode: Int, info: String): String {
        return Dispatch.call(this, "SetExcludeRegion", mode, info).string
    }

    /**
     * 函数简介: 设置图片密码，如果图片本身没有加密，那么此设置不影响不加密的图片，一样正常使用
     * @param pwd  String 图片密码
     * @return Boolean true表示成功
     * 注意此函数必须在使用图片之前调用
     */
    fun Dispatch.setPicPwd(pwd: String): Boolean {
        return Dispatch.call(this, "SetPicPwd", pwd).int == 1
    }

}