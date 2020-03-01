package base.utils.dm

import com.jacob.com.Dispatch

interface CharRecognitionUtils{

    /**
     * 设置字库文件
     * @param index 整形数:字库的序号,取值为0-9,目前最多支持10个字库
     * @param file 字符串:字库文件名
     */
    fun Dispatch.setDict(index: Int, file: String): Boolean {
        return Dispatch.call(this, "SetDict", index, file).int == 1
    }

    /**
     * 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串,并且相似度为sim,sim取值范围(0.1-1.0),
     * 这个值越大越精确,越大速度越快,越小速度越慢,请斟酌使用!
     * @param x1 整形数:区域的左上X坐标
     * @param y1 整形数:区域的左上Y坐标
     * @param x2 整形数:区域的右下X坐标
     * @param y2 整形数:区域的右下Y坐标
     * @param color_format 字符串:颜色格式串. 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.
     * 注意，RGB和HSV格式都支持.
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串
     */
    fun Dispatch.ocr(x1: Int, y1: Int, x2: Int, y2: Int, color_format: String, sim: Double): String {
        return Dispatch.call(this, "Ocr", x1, y1, x2, y2, color_format, sim).string
    }

//    string FindStrFastE(x1,y1,x2,y2,string,color_format,sim)
    /**
     * 快速在指定区域内查找文字
     *
     * @param x1 整形数:区域的左上X坐标
     * @param y1 整形数:区域的左上Y坐标
     * @param x2 整形数:区域的右下X坐标
     * @param y2 整形数:区域的右下Y坐标
     * @param string 字符串:待查找的字符串, 可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 字符串:颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV格式都支持.
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @return 返回字符串序号以及X和Y坐标,形式如"id|x|y", 比如"0|100|200",没找到时，id和X以及Y均为-1，"-1|-1|-1"
     *
     * 注: 此函数比FindStrE要快很多，尤其是在字库很大时，或者模糊识别时，效果非常明显。推荐使用此函数。
     * 另外由于此函数是只识别待查找的字符，所以可能会有如下情况出现问题。
     *
     * 比如 字库中有"张和三" 一共3个字符数据，然后待识别区域里是"张和三",如果用FindStrE查找
     * "张三"肯定是找不到的，但是用FindStrFastE却可以找到，因为"和"这个字符没有列入查找计划中
     * 所以，在使用此函数时，也要特别注意这一点。
     */
    fun Dispatch.findStrFast(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String, sim: Double): String {
        val result = Dispatch.call(this, "FindStrFastE", x1, y1, x2, y2, string, color_format, sim).string
        println("##########文字\"${string}\"的查找结果是$result")
        return result
    }

    fun Dispatch.findStr(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String, sim: Double): String {
        val result = Dispatch.call(this, "FindStrE", x1, y1, x2, y2, string, color_format, sim).string
        println("##########文字\"${string}\"的查找结果是$result")
        return result
    }

    fun Dispatch.findStrFastEx(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String, sim: Double): String {
        val result = Dispatch.call(this, "FindStrFastEx", x1, y1, x2, y2, string, color_format, sim).string
        println("##########文字\"${string}\"的查找结果是$result")
        return result
    }

}