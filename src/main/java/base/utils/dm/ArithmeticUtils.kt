package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 算法
 */
interface ArithmeticUtils {

    /**
     * 函数简介: 根据部分Ex接口的返回值，排除指定范围区域内的坐标
     * @param all_pos String 坐标描述串
     * 一般是FindStrEx、FindStrFastEx、FindStrWithFontEx、FindColorEx、FindMultiColorEx和FindPicEx的返回值
     * @param type Int 取值为0或者1
     * 如果all_pos的内容是由FindPicEx,FindPicMemEx,FindStrEx,FindStrFastEx,FindStrWithFontEx返回，那么取值为0
     * 如果all_pos的内容是由FindColorEx, FindMultiColorEx,FindColorBlockEx,FindShapeEx返回，那么取值为1
     * 如果all_pos的内容是由OcrEx返回，那么取值为2
     * 如果all_pos的内容是由FindPicExS,FindStrExS,FindStrFastExS返回，那么取值为3
     * @param x1 Int
     * @param y1 Int
     * @param x2 Int
     * @param y2 Int
     * @return String 经过筛选以后的返回值，格式和type指定的一致
     * 代码示例
     * <code>
     *      ret = dm.FindColorEx(0,0,2000,2000,"aaaaaa-000000",1.0,0)
     *      ret = dm.ExcludePos(ret,1,100,100,300,400)
     *      TracePrint ret
     *      ret = dm.FindPicEx(0,0,2000,2000,"a.bmp","000000",1.0,0)
     *      ret = dm.ExcludePos(ret,0,100,100,300,400)
     *      TracePrint ret
     *      ret = dm.OcrEx(0,0,2000,2000,"ffffff",1.0)
     *      ret = dm.ExcludePos(ret,2,100,100,300,400)
     *      TracePrint ret
     *      ret = dm.FindPicExS(0,0,2000,2000,"test.bmp|test2.bmp","020202",1.0,0)
     *      ret = dm.ExcludePos(ret,3,100,100,300,400)
     *      TracePrint ret
     * </code>
     */
    fun Dispatch.excludePos(all_pos: String, type: Int, x1: Int, y1: Int, x2: Int, y2: Int): String {
        return Dispatch.call(this, "ExcludePos", all_pos, type, x1, y1, x2, y2).string
    }

    /**
     * 函数简介: 根据部分Ex接口的返回值，然后在所有坐标里找出距离指定坐标最近的那个坐标
     * @param all_pos String 坐标描述串
     * 一般是FindStrEx、FindStrFastEx、FindStrWithFontEx、FindColorEx、FindMultiColorEx和FindPicEx的返回值
     * @param type Int 取值为0或者1
     * 如果all_pos的内容是由FindPicEx,FindPicMemEx,FindStrEx,FindStrFastEx,FindStrWithFontEx返回，那么取值为0
     * 如果all_pos的内容是由FindColorEx, FindMultiColorEx,FindColorBlockEx,FindShapeEx返回，那么取值为1
     * 如果all_pos的内容是由OcrEx返回，那么取值为2
     * 如果all_pos的内容是由FindPicExS,FindStrExS,FindStrFastExS返回，那么取值为3
     * @param x Int
     * @param y Int
     * @return String 返回的格式和type有关
     * 如果type为0，那么返回的格式是"id,x,y"
     * 如果type为1，那么返回的格式是"x,y"
     * 代码示例
     * <code>
     *     ret = dm.FindColorEx(0,0,2000,2000,"aaaaaa-000000",1.0,0)
     *     ret = dm.FindNearestPos(ret,1,100,100)
     *     TracePrint ret
     *     ret = dm.FindPicEx(0,0,2000,2000,"a.bmp","000000",1.0,0)
     *     ret = dm.FindNearestPos(ret,0,100,100)
     *     TracePrint ret
     *     ret = dm.OcrEx(0,0,2000,2000,"ffffff",1.0)
     *     ret = dm.FindNearestPos(ret,2,100,100)
     *     TracePrint ret
     *     ret = dm.FindPicExS(0,0,2000,2000,"test.bmp|test2.bmp","020202",1.0,0)
     *     ret = dm.FindNearestPos(ret,3,100,100)
     *     TracePrint ret
     * </code>
     */
    fun Dispatch.findNearestPos(all_pos: String, type: Int, x: Int, y: Int): String {
        return Dispatch.call(this, "FindNearestPos", all_pos, type, x, y).string
    }

    /**
     * 函数简介: 根据部分Ex接口的返回值，然后对所有坐标根据对指定坐标的距离(或者指定X或者Y)进行从小到大的排序
     * @param all_pos String 坐标描述串
     * 一般是FindStrEx、FindStrFastEx、FindStrWithFontEx、FindColorEx、FindMultiColorEx和FindPicEx的返回值
     * @param type Int 取值为0或者1
     * 如果all_pos的内容是由FindPicEx,FindPicMemEx,FindStrEx,FindStrFastEx,FindStrWithFontEx返回，那么取值为0
     * 如果all_pos的内容是由FindColorEx, FindMultiColorEx,FindColorBlockEx,FindShapeEx返回，那么取值为1
     * 如果all_pos的内容是由OcrEx返回，那么取值为2
     * 如果all_pos的内容是由FindPicExS,FindStrExS,FindStrFastExS返回，那么取值为3
     * @param x Int
     * @param y Int
     * @return String 返回的格式和type指定的格式一致
     */
    fun Dispatch.sortPosDistance(all_pos: String, type: Int, x: Int, y: Int): String {
        return Dispatch.call(this, "SortPosDistance", all_pos, type, x, y).string
    }

}