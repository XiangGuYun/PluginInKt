package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 大漠文字识别模块
 */
interface CharRecognitionUtils {

    /**
     * 函数简介: 设置字库文件
     * @param index Int 字库的序号,取值为0-9,目前最多支持10个字库
     * @param file String 字库文件名
     * return Boolean true 成功 false 失败
     */
    fun Dispatch.setDict(index: Int, file: String): Boolean {
        return Dispatch.call(this, "SetDict", index, file).int == 1
    }

    /**
     * 函数简介: 表示使用哪个字库文件进行识别(index范围:0-99)，设置之后，永久生效，除非再次设定
     * @param index Int 字库编号(0-99)
     * return Boolean true 成功 false 失败
     */
    fun Dispatch.useDict(index: Int): Boolean {
        return Dispatch.call(this, "useDict", index).int == 1
    }

    /**
     * 函数简介: 给指定的字库中添加一条字库信息
     * @param index Int 字库的序号，取值为0-99，目前最多支持100个字库
     * @param dict_info String 字库描述串，具体参考大漠综合工具中的字符定义
     * return Boolean true 成功 false 失败
     * 示例: dm_ret = dm.AddDict(0,"081101BF8020089FD10A21443F85038$记$0.0$11")
     * 注意: 此函数尽量在小字库中使用，大字库中使用AddDict速度比较慢
     * 另外，此函数是向指定的字库所在的内存中添加,而不是往文件中添加
     * 添加以后立刻就可以用于文字识别，无须再SetDict
     * 如果要保存添加进去的字库信息，需要调用SaveDict
     */
    fun Dispatch.addDict(index: Int, dict_info: String): Boolean {
        return Dispatch.call(this, "AddDict", index, dict_info).int == 1
    }

    /**
     * 函数简介: 保存指定的字库到指定的文件中
     * @param index Int 字库索引序号 取值为0-99对应100个字库
     * @param file String 文件名
     * return Boolean true 成功 false 失败
     * 代码示例
     * <code>
     *     dm.SetPath "c:\test_game"
     *     dm.AddDict 0,"FFF00A7D49292524A7D402805FFC$回$0.0.54$11"
     *     dm.AddDict 0,"3F0020087FF08270B9A108268708808$收$0.0.43$11"
     *     dm.AddDict 0,"2055C98617420807C097F222447C800$站$0.0.44$11"
     *     dm.SaveDict 0,"test.txt"
     * </code>
     */
    fun Dispatch.saveDict(index: Int, file: String): Boolean {
        return Dispatch.call(this, "SaveDict", index, file).int == 1
    }

    /**
     * 函数简介: 清空指定的字库.
     * @param index Int 字库的序号，取值为0-99，目前最多支持100个字库
     * return Boolean true 成功 false 失败
     * 注意: 此函数尽量在小字库中使用，大字库中使用clearDict速度比较慢
     * 另外，此函数支持清空内存中的字库，而不是字库文件本身
     */
    fun Dispatch.clearDict(index: Int): Boolean {
        return Dispatch.call(this, "ClearDict", index).int == 1
    }

    /**
     * 函数简介: 允许当前调用的对象使用全局字库
     * 如果你的程序中DM对象太多，并且每个DM对象都用到了同样的字库，
     * 可以考虑用全局字库，这样可以节省大量内存
     * @param enable Boolean true 打开 false 关闭
     * @return Boolean true 成功 false 失败
     */
    fun Dispatch.enableShareDict(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "EnableShareDict", enable).int == 1
    }

    /**
     * 函数简介: 根据指定的范围，以及指定的颜色描述，提取点阵信息，类似于大漠工具里的单独提取
     * @param x1 Int 左上角X坐标
     * @param y1 Int 左上角Y坐标
     * @param x2 Int 右下角X坐标
     * @param y2 Int 右下角Y坐标
     * @param color String 颜色格式串(RGB和、HSV、灰度格式都支持)。
     * @param word String 待定义的文字(不能为空，且不能为关键符号"$")
     * @return String 识别到的点阵信息，可用于AddDict，如果失败，返回空
     */
    fun Dispatch.fetchWord(x1: Int, y1: Int, x2: Int, y2: Int, color: String, word: String): Boolean {
        return Dispatch.call(this, "FetchWord", x1, y1, x2, y2, color, word).int == 1
    }

    /**
     * 函数简介: 在屏幕范围(x1,y1,x2,y2)内，查找string(可以是任意个字符串的组合)，
     * 并返回符合color_format的坐标位置，相似度sim同Ocr接口描述(多色，差色查找类似于Ocr接口，不再重述)
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param string String 待查找的字符串,可以是字符串组合，比如"长安|洛阳|大雁塔"，中间用"|"来分割字符串
     * @param color_format String 颜色格式串(RGB和、HSV、灰度格式都支持)，可以包含换行分隔符，语法是","后加分割字符串
     * @param sim Double 相似度，取值范围0.1-1.0
     * @param isFast Boolean 是否采用更快速的函数(FindStrFastE)
     * @return String 返回所有找到的坐标集合，格式如下: "id,x0,y0|id,x1,y1|......|id,xn,yn"
     * 比如"0,100,20|2,30,40" 表示找到了两个，第一个对应的是序号为0的字符串，坐标是(100,20)，第二个是序号为2的字符串，坐标(30,40)
     * 注: 此函数的原理是先Ocr识别，然后再查找。所以速度比FindStrExFast要慢，尤其是在字库很大，或者模糊度不为1.0时
     * 一般字库字符数量小于100左右，模糊度为1.0时，用FindStrEx要快一些，否则用FindStrFastEx
     */
    fun Dispatch.findStr(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String,
                         sim: Double, isFast: Boolean = false): String {
        return Dispatch.call(this, if (!isFast) "FindStrE" else "FindStrFastE",
                x1, y1, x2, y2, string, color_format, sim).string
    }

    /**
     * 函数简介: 在屏幕范围(x1,y1,x2,y2)内，查找string(可以是任意字符串的组合)，
     * 并返回符合color_format的所有坐标位置，相似度sim同Ocr接口描述(多色，差色查找类似于Ocr接口，不再重述)
     * 注：此函数基本等同于findStr，唯一区别在于返回对应字符换的坐标位置数量是单个还是多个
     */
    fun Dispatch.findStrEx(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String,
                           sim: Double, isFast: Boolean = false): String {
        return Dispatch.call(this, if (!isFast) "FindStrEx" else "FindStrFastEx", x1, y1, x2, y2,
                string, color_format, sim).string
    }

    /**
     * 函数简介: 在屏幕范围(x1,y1,x2,y2)内，查找string(可以是任意字符串的组合)，
     * 并返回符合color_format的所有坐标位置，相似度sim同Ocr接口描述(多色，差色查找类似于Ocr接口，不再重述)
     * 注：此函数同FindStrEx，只是返回值不同
     * @return String
     * 返回所有找到的坐标集合,格式如下:
     * "str,x0,y0| str,x1,y1|......| str,xn,yn"
     * 比如 "长安,100,20|大雁塔,30,40" 表示找到了两个: 第一个是长安且坐标是(100,20)，第二个是大雁塔且坐标是(30,40)
     */
    fun Dispatch.findStrExS(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String,
                            sim: Double, isFast: Boolean = false): String {
        return Dispatch.call(this, if (!isFast) "FindStrExS" else "FindStrFastExS",
                x1, y1, x2, y2, string, color_format, sim).string
    }

    /**
     * 函数简介: 同FindStrE，但是不使用SetDict设置的字库，而利用系统自带的字库，速度比FindStrE稍慢
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param string String 待查找的字符串，可以是字符串组合，比如"长安|洛阳|大雁塔"，中间用"|"来分割字符串
     * @param color_format String 颜色格式串(RGB和、HSV、灰度格式都支持)，可以包含换行分隔符，语法是","后加分割字符串
     * @param sim Double 相似度，取值范围0.1-1.0
     * @param font_name String 系统字体名，比如"宋体"
     * @param font_size Int 系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准
     * @param flag Int 字体类别 取值可以是以下值的组合，比如1+2+4+8，2+4，0表示正常字体
     * 1 : 粗体
     * 2 : 斜体
     * 4 : 下划线
     * 8 : 删除线
     * @return String 返回字符串序号以及X和Y坐标，形式如"id|x|y"，比如"0|100|200"，没找到时id和X以及Y均为-1: "-1|-1|-1"

     */
    fun Dispatch.findStrWithFont(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String,
                                 sim: Double, font_name: String, font_size: Int, flag: Int): String {
        return Dispatch.call(this, "FindStrWithFontE",
                x1, y1, x2, y2, string, color_format, sim, font_name, font_size, flag).string
    }

    /**
     * 函数简介: 同FindStrEx，但是不使用SetDict设置的字库，而利用系统自带的字库，速度比FindStrEx稍慢
     */
    fun Dispatch.findStrWithFontEx(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String,
                                   sim: Double, font_name: String, font_size: Int, flag: Int): String {
        return Dispatch.call(this, "FindStrWithFontEx",
                x1, y1, x2, y2, string, color_format, sim, font_name, font_size, flag).string
    }

    /**
     * 函数简介: 获取指定字库中指定条目的字库信息
     * @param index Int 字库序号(0-99)
     * @param font_index Int 字库条目序号(从0开始计数,数值不得超过指定字库的字库上限,具体参考GetDictCount)
     * @return 返回字库条目信息. 失败返回空串
     */
    fun Dispatch.getDict(index: Int, font_index: Int): String {
        return Dispatch.call(this, "GetDict", index, font_index).string
    }

    /**
     * 函数简介: 获取指定的字库中的字符数量
     * @param index Int 字库序号(0-99)
     * @return Int字库数量
     */
    fun Dispatch.getDictCount(index: Int): Int {
        return Dispatch.call(this, "GetDictCount", index).int
    }

    /**
     * 函数简介: 根据指定的文字，以及指定的系统字库信息，获取字库描述信息
     * @param str String 需要获取的字符串
     * @param font_name String 系统字体名，比如"宋体"
     * @param font_size Int 系统字体尺寸，这个尺寸一定要以大漠综合工具获取的为准
     * @param flag Int 字体类别 取值可以是以下值的组合，比如1+2+4+8，2+4，0表示正常字体
     * 1 : 粗体
     * 2 : 斜体
     * 4 : 下划线
     * 8 : 删除线
     * @return String 返回字库信息，每个字符的字库信息用"|"来分割
     */
    fun Dispatch.getDictInfo(str: String, font_name: String, font_size: Int, flag: Int): String {
        return Dispatch.call(this, "GetDictInfo", str, font_name, font_size, flag).string
    }

    /**
     * 函数简介: 获取当前使用的字库序号(0-99)
     * @return Int 字库序号(0-99)
     */
    fun Dispatch.getNowDict(index: Int): Int {
        return Dispatch.call(this, "GetNowDict", index).int
    }

    /**
     * 函数简介: 对插件部分接口的返回值进行解析，并返回ret中的坐标个数
     * @param ret String 部分接口的返回串
     * @return Int 返回ret中的坐标个数
     */
    fun Dispatch.getResultCount(ret: String): Int {
        return Dispatch.call(this, "GetResultCount", ret).int
    }

    /**
     * 函数简介: 根据指定的范围，以及设定好的词组识别参数(一般不用更改，除非你真的理解了)
     * 识别这个范围内所有满足条件的词组(比较适合用在未知文字的情况下，进行不定识别)
     * @param x1 Int 左上角X坐标
     * @param y1 Int 左上角Y坐标
     * @param x2 Int 右下角X坐标
     * @param y2 Int 右下角Y坐标
     * @param color String 颜色格式串(RGB和、HSV、灰度格式都支持)
     * @param sim Double 相似度 0.1-1.0
     * @return String 识别到的格式串(要用到专用函数来解析)
     */
    fun Dispatch.getWords(x1: Int, y1: Int, x2: Int, y2: Int, color: String, sim: Double): String {
        return Dispatch.call(this, "GetWords", x1, y1, x2, y2, color, sim).string
    }

    /**
     * 函数简介: 根据指定的范围，以及设定好的词组识别参数(一般不用更改，除非你真的理解了)
     * 识别这个范围内所有满足条件的词组，这个识别函数不会用到字库，只是识别大概形状的位置
     * @param x1 Int 左上角X坐标
     * @param y1 Int 左上角Y坐标
     * @param x2 Int 右下角X坐标
     * @param y2 Int 右下角Y坐标
     * @param color String 颜色格式串(RGB和、HSV、灰度格式都支持)
     * @param sim Double 相似度 0.1-1.0
     * @return String 识别到的格式串(要用到专用函数来解析)
     */
    fun Dispatch.getWordsNoDict(x1: Int, y1: Int, x2: Int, y2: Int, color: String, sim: Double): String {
        return Dispatch.call(this, "GetWordsNoDict", x1, y1, x2, y2, color, sim).string
    }

    /**
     * 函数简介: 在使用GetWords进行词组识别以后，可以用此接口进行识别各个词组的内容
     * @param str String GetWords的返回值
     * @param index Int 表示第几个词组
     * @return 返回的第index个词组内容
     */
    fun Dispatch.getWordResultStr(str: String, index: Int): String {
        return Dispatch.call(this, "GetWordResultStr", str, index).string
    }

    /**
     * 函数简介: 在使用GetWords进行词组识别以后，可以用此接口进行识别词组数量的计算
     * @param str String GetWords接口调用以后的返回值
     * @return Int 返回词组数量
     */
    fun Dispatch.getWordResultCount(str: String): Int {
        return Dispatch.call(this, "GetWordResultCount", str).int
    }

    /**
     * 函数简介: 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串，并且相似度为sim(范围0.1-1.0)，
     * 这个值越大越精确，越大速度越快，越小速度越慢，请斟酌使用!
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param color_format String 颜色格式串(RGB和HSV格式都支持)。可以包含换行分隔符，语法是","后加分割字符串
     * @param sim Double 相似度(范围0.1-1.0)
     * @return 返回识别到的字符串
     */
    fun Dispatch.ocr(x1: Int, y1: Int, x2: Int, y2: Int, color_format: String, sim: Double): String {
        return Dispatch.call(this, "Ocr", x1, y1, x2, y2, color_format, sim).string
    }

    /**
     * 函数简介: 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串，并且相似度为sim(范围0.1-1.0)，
     * 这个值越大越精确，越大速度越快，越小速度越慢，请斟酌使用!
     * 这个函数可以返回识别到的字符串，以及每个字符的坐标
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param color_format String 颜色格式串(RGB和HSV格式都支持)，不支持换行分隔
     * @param sim Double 相似度(范围0.1-1.0)
     * @return 返回识别到的字符串，格式如: "字符0$x0$y0|…|字符n$xn$yn"
     */
    fun Dispatch.ocrEx(x1: Int, y1: Int, x2: Int, y2: Int, color_format: String, sim: Double): String {
        return Dispatch.call(this, "OcrEx", x1, y1, x2, y2, color_format, sim).string
    }

    /**
     * 函数简介: 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串，并且相似度为sim(范围0.1-1.0)，
     * 这个值越大越精确，越大速度越快，越小速度越慢，请斟酌使用!
     * 这个函数可以返回识别到的字符串，以及每个字符的坐标(同OcrEx，区别于返回形式)
     * @return 返回识别到的字符串，格式如: "识别到的信息|x0,y0|…|xn,yn"
     * 代码示例
     * <code>
     *     ss = dm.OcrExOne(0,0,2000,2000,"ffffff|000000",1.0)
     *     ss = split(ss,"|")
     *     MessageBox "识别到的String "&ss(0)
     *     ss_len = len(ss(0))
     *     for i = 1 to ss_len
     *     MessageBox "第("&i&")的坐标是"&ss(i)
     *     next
     * </code>
     */
    fun Dispatch.ocrExOne(x1: Int, y1: Int, x2: Int, y2: Int, color_format: String, sim: Double): String {
        return Dispatch.call(this, "OcrExOne", x1, y1, x2, y2, color_format, sim).string
    }

    /**
     * 函数简介: 识别位图中区域(x1,y1,x2,y2)的文字
     * @param x1 Int 区域的左上X坐标
     * @param y1 Int 区域的左上Y坐标
     * @param x2 Int 区域的右下X坐标
     * @param y2 Int 区域的右下Y坐标
     * @param color_format String 颜色格式串(RGB和HSV格式都支持)，不支持换行分隔
     * @param sim Double 相似度(范围0.1-1.0)
     * @return 返回识别到的字符串
     */
    fun Dispatch.ocrInFile(x1: Int, y1: Int, x2: Int, y2: Int, pic_name: String, color_format: String, sim: Double): String {
        return Dispatch.call(this, "OcrInFile", x1, y1, x2, y2, color_format, sim).string
    }

}