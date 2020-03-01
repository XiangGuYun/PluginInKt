package base.utils.dm

import com.jacob.com.Dispatch

interface MemoryUtils {
    /**
     * 对指定地址写入二进制数据
     */
    fun Dispatch.writeData(wh: Int, addr: String, data: String): Boolean {
        return Dispatch.call(this, "WriteData", wh, addr, data).int == 1
    }

    /**
     * 对指定地址写入字符串，可以是Ascii字符串或者是Unicode字符串
     * @param type 整形数: 字符串类型,取值如下
     * 0 : Ascii字符串
     * 1 : Unicode字符串
     * 2 : UTF8字符串
     */
    fun Dispatch.writeString(wh: Int, addr: String, type: Int, data: String): Boolean {
        return Dispatch.call(this, "WriteString", wh, addr, type, data).int == 1
    }

    /**
     * 读取指定地址的整数数值，类型可以是8位，16位  32位 或者64位
     * @param wh 整形数: 指定搜索的窗口句柄或者进程ID.  默认是窗口句柄. 如果要指定为进程ID,需要调用SetMemoryHwndAsProcessId
     * @param addr 字符串: 用字符串来描述地址，类似于CE的地址描述，数值必须是16进制,里面可以用[ ] + -这些符号来描述一个地址。
     * +表示地址加，-表示地址减。模块名必须用<>符号来圈起来
     * 例如:
     * 1. "4DA678" 最简单的方式，用绝对数值来表示地址
     * 2. "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
     * 3. "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
     * 4. "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
     * 5."[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
     * @param type 整形数: 整数类型,取值如下
     *  0 : 32位有符号
     *  1 : 16 位有符号
     *  2 : 8位有符号
     *  3 : 64位
     *  4 : 32位无符号
     *  5 : 16位无符号
     *  6 : 8位无符号
     */
    fun Dispatch.readInt(wh: Int, addr: String, type: Int): Long {
        return Dispatch.call(this, "ReadInt", wh, addr, type).long
    }

    enum class Type {
        BIT32, BIT16, BIT8
    }

    /**
     * 对指定地址写入整数数值，类型可以是8位，16位 或者 32位
     */
    fun Dispatch.writeInt(wh: Int, addr: String, type: Type, intValue: Int): Boolean {
        return Dispatch.call(this, "WriteInt", wh, addr,
                when (type) {
                    Type.BIT32 -> 0
                    Type.BIT16 -> 1
                    else -> 2
                }, intValue).int == 1
    }
}