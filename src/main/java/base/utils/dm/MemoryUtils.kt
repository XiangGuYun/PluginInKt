package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 大漠内存模块
 *
 * 问题: 如何用字符串来描述内存地址？
 * 类似于CE的地址描述，数值必须是16进制
 * 里面可以用[ ] + -这些符号来描述一个地址。+表示地址加，-表示地址减，模块名必须用<>符号来圈起来
 *  例如:
 *  1. "4DA678" 最简单的方式，用绝对数值来表示地址
 *  2. "<360SE.exe>+DA678" 相对简单的方式，只是这里用模块名来决定模块基址，后面的是偏移
 *  3. "[4DA678]+3A" 用绝对数值加偏移，相当于一级指针
 *  4. "[<360SE.exe>+DA678]+3A" 用模块定基址的方式，也是一级指针
 *  5. "[[[<360SE.exe>+DA678]+3A]+5B]+8" 这个是一个三级指针
 */
interface MemoryUtils {

    /*
     ********************************* 查找内存函数 *********************************
     */

    /**
     * 函数简介: 搜索指定的二进制数据(默认步长是1，如果要定制步长，请用FindDataEx)
     * @param hwnd String 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @param addr_range String 指定搜索的地址集合
     * 这个地方可以是上次FindXXX的返回地址集合，可以进行二次搜索(类似CE的再次扫描)
     * 如果要进行地址范围搜索，那么这个值为的形如如下(类似于CE的新搜索)
     * "00400000-7FFFFFFF" "80000000-BFFFFFFF" "00000000-FFFFFFFF" 等
     * @param data String 要搜索的二进制数据
     * 以字符串的形式描述比如("00 01 23 45 67 86 ab ce f1"等)
     * 这里也可以支持模糊查找，用??来代替单个字节(比如"00 01 ?? ?? 67 86 ?? ce f1"等)
     * @return String 返回搜索到的地址集合，地址格式如下:
     * "addr1|addr2|addr3…|addrn"
     * 比如"400050|423435|453430"
     * 如果要想知道函数是否执行成功，请查看GetLastError函数
     */
    fun Dispatch.findData(hwnd: Int, data: String, addr_range: String = "00000000-FFFFFFFF"): String {
        return Dispatch.call(this, "FindData", hwnd, addr_range, data).string
    }

    /**
     * 函数简介: 搜索指定的二进制数据
     * @param hwnd String
     * @param addr_range String
     * @param data String
     * @param step Int 搜索步长
     * @param multi_thread Boolean 表示是否开启多线程查找，开启多线程查找速度较快，但会耗费较多CPU资源
     * @param mode Int 1 表示开启快速扫描(略过只读内存)  0 表示所有内存类型全部扫描
     * @return String
     */
    fun Dispatch.findDataEx(hwnd: Int, data: String, step: Int, multi_thread: Boolean, mode: Int,
                            addr_range: String = "00000000-FFFFFFFF"): String {
        return Dispatch.call(this, "FindDataEx",
                hwnd, addr_range, data, step, if (multi_thread) 1 else 0, mode).string
    }

    /**
     * 搜索指定的字符串(默认步长是1，如果要定制步长，请用FindStringEx)
     * @param hwnd String
     * @param addr_range String
     * @param string String
     * @param type Int 搜索的字符串类型
     *  0 : Ascii字符串
     *  1 : Unicode字符串
     *  2 : UTF8字符串
     * @return String
     */
    fun Dispatch.findString(hwnd: Int, string: String, type: Int = 2, addr_range: String="00000000-FFFFFFFF"): String {
        return Dispatch.call(this, "FindString", hwnd, addr_range, string, type).string
    }

    /**
     * 搜索指定的字符串
     * @param hwnd String
     * @param addr_range String
     * @param string String
     * @param type Int
     * @param step Int
     * @param multi_thread Boolean
     * @param mode Int
     * @return String
     */
    fun Dispatch.findStringEx(hwnd: Int, addr_range: String, string: String, type: Int,
                              step: Int, multi_thread: Boolean, mode: Int): String {
        return Dispatch.call(this, "FindStringEx",
                hwnd, addr_range, string, type, step, if (multi_thread) 1 else 0, mode).string
    }

    /**
     * 搜索指定的数字(默认步长是1，如果要定制步长，请用FindNumberEx)
     * @param hwnd String
     * @param addr_range String
     * @param minValue Number 搜索的数值最小值
     * @param maxValue Number 搜索的数值最大值
     * 最终搜索的数值大与等于minValue，并且小于等于maxValue，注意minValue和maxValue必须是同一种数值类型！
     * @return String
     */
    fun Dispatch.findNumber(hwnd: Int, minValue: Number, maxValue: Number, addr_range: String = "00000000-FFFFFFFF"): String {
        if (minValue.javaClass.simpleName != maxValue.javaClass.simpleName) {
            throw Exception("最大值和最小值类型不统一")
        }
        return Dispatch.call(this,
                when (minValue) {
                    is Int -> "FindInt"
                    is Float -> "FindFloat"
                    else -> "FindDouble"
                },
                hwnd, addr_range, minValue, maxValue).string
    }

    /**
     * 搜索指定的双精度浮点数
     * @param hwnd String
     * @param addr_range String
     * @param minValue Number
     * @param maxValue Number
     * @param step Int
     * @param multi_thread Boolean
     * @param mode Int
     * @return String
     */
    fun Dispatch.findNumberEx(hwnd: Int, minValue: Number, maxValue: Number, step: Int,
                              multi_thread: Boolean, mode: Int, addr_range: String = "00000000-FFFFFFFF"): String {
        if (minValue.javaClass.simpleName != maxValue.javaClass.simpleName) {
            throw Exception("最大值和最小值类型不统一")
        }
        return Dispatch.call(this,
                when (minValue) {
                    is Int -> "FindInt"
                    is Float -> "FindFloat"
                    else -> "FindDouble"
                },
                hwnd, addr_range, minValue, maxValue, step, if (multi_thread) 1 else 0, mode).string
    }

    /*
     ********************************* 读取内存函数 *********************************
     */

    /**
     * 函数简介: 读取指定地址的整数数值，类型可以是8位、16位、32位、64位
     * @param hwnd Int 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @param addr Any 既可以用字符串来描述地址(如何描述详见该接口说明)，也可以用整型来直接表示地址
     * @param type Int 整数类型
     *   0 : 32位
     *   1 : 16位
     *   2 : 8位
     *   3 : 64位
     *   4 : 32位无符号
     *   5 : 16位无符号
     *   6 : 8位无符号
     * @return Int 读取到的数值
     */
    fun Dispatch.readInt(hwnd: Int, addr: Any, type: Int = 0): Int {
        return Dispatch.call(this, if (addr is String) "ReadInt" else "ReadIntAddr", hwnd, addr, type).int
    }

    fun Dispatch.readFloat(hwnd: Int, addr: Any): Float {
        return Dispatch.call(this, if (addr is String) "ReadFloat" else "ReadFloatAddr", hwnd, addr).float
    }

    fun Dispatch.readDouble(hwnd: Int, addr: Any): Double {
        return Dispatch.call(this, if (addr is String) "ReadDouble" else "ReadDoubleAddr", hwnd, addr).double
    }

    /**
     * 函数简介: 读取指定地址的字符串，可以是GBK字符串或者是Unicode字符串(必须事先知道内存区的字符串编码方式)
     * @param hwnd Int 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @param addr Any 既可以用字符串来描述地址(如何描述详见该接口说明)，也可以用整型来直接表示地址
     * @param type Int 字符串类型
     * 0 : GBK字符串
     * 1 : Unicode字符串
     * 2 : UTF8字符串
     * @param len Int 需要读取的字节数目(如果为0，则自动判定字符串长度)
     * @return String 读取到的字符串，如果要想知道函数是否执行成功，请查看GetLastError函数
     */
    fun Dispatch.readString(hwnd: Int, addr: Any, type: Int, len: Int): String {
        return Dispatch.call(this, if (addr is String) "ReadString" else "ReadStringAddr", hwnd, addr, type, len).string
    }

    /*
     ********************************* 修改内存函数 *********************************
     */
    /**
     * 函数简介: 对指定地址写入二进制数据
     * @param addr Any 既可以用字符串来描述地址(如何描述详见该接口说明)，也可以用整型来直接表示地址
     * @param data String 二进制数据，以字符串形式描述，比如"12 34 56 78 90 ab cd"
     * @return Boolean 写入是否成功
     */
    fun Dispatch.writeData(wh: Int, addr: Any, data: String): Boolean {
        return Dispatch.call(this, if (addr is String) "WriteData" else "WriteDataAddr", wh, addr, data).int == 1
    }

    /**
     * 函数简介: 对指定地址写入字符串，可以是Ascii字符串或者是Unicode字符串
     * @param addr Any 既可以用字符串来描述地址(如何描述详见该接口说明)，也可以用整型来直接表示地址
     * @param data String 写入的字符串
     * @param type 整形数: 字符串类型,取值如下
     * 0 : Ascii字符串
     * 1 : Unicode字符串
     * 2 : UTF8字符串
     * @return Boolean 写入是否成功
     */
    fun Dispatch.writeString(wh: Int, addr: Any, data: String, type: Int = 0): Boolean {
        return Dispatch.call(this, if (addr is String) "WriteString" else "WriteStringAddr", wh, addr, type, data).int == 1
    }

    /**
     * 函数简介: 对指定地址写入整数数值
     * @param hwnd Int 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @param addr Any 既可以用字符串来描述地址(如何描述详见该接口说明)，也可以用整型来直接表示地址
     * @param value Number 写入的数值，可以是Int、Float、Double
     * @param type Int 如果value是整形，那么可以指定整型的类型
     *  0 : 32位
     *  1 : 16位
     *  2 : 8位
     *  3 : 64位
     * @return Boolean
     */
    fun Dispatch.writeNumber(hwnd: Int, addr: Any, value: Number, type: Int = 0): Boolean {
        return if (value is Int) {
            Dispatch.call(this, if (addr is String) "WriteInt" else "WriteIntAddr", hwnd, addr, type, value).int == 1
        } else {
            Dispatch.call(this, when (value) {
                is Double -> if (addr is String) "WriteDouble" else "WriteDoubleAddr"
                else -> if (addr is String) "WriteFloat" else "WriteFloatAddr"
            }, hwnd, addr, value).int == 1
        }
    }

    /*
     ********************************* 类型转换函数 *********************************
     */

    /**
     * 函数简介: 把数字转换成二进制形式
     * @param value Number 需要转化的数字
     * @return String 字符串形式表达的二进制数据，可以用于WriteData FindData FindDataEx等接口
     * 示例:
     * double_data =  dm.DoubleToData(1.24)
     * dm_ret = dm.FindData(hwnd,"00000000-7fffffff",double_data)
     */
    fun Dispatch.numberToData(value: Number): String {
        return Dispatch.call(this, when (value) {
            is Int -> "IntToData"
            is Float -> "FloatToData"
            else -> "DoubleToData"
        }, value).string
    }

    /**
     * 函数简介: 把字符串转换成二进制形式
     * @param value String 需要转化的字符串
     * @param type Int 0: 返回Ascii表达的字符串 1: 返回Unicode表达的字符串
     * @return String
     */
    fun Dispatch.stringToData(value: String, type: Int): String {
        return Dispatch.call(this, "StringToData", value, type).string
    }

    /*
    ********************************* 进程相关函数 *********************************
    */

    /**
     * 函数简介: 释放指定进程的不常用内存
     * @param hwnd Int 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @return Boolean
     * 注: 此函数的原理并不是真正的释放进程内存，而是把进程中不常用的内存交换到虚拟内存中(硬盘里)，这样可以空余出系统ram
     * 此函数会加大系统内存和硬盘之间的数据交换频率，不能频繁调用，一般用法是程序长时间运行一段时间调用一次
     */
    fun Dispatch.freeProcessMemory(hwnd: Int): Boolean {
        return Dispatch.call(this, "FreeProcessMemory", hwnd).int == 1
    }

    /**
     * 函数简介: 获取指定窗口所在进程的启动命令行
     * @param hwnd Int 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @return String 读取到的启动命令行
     */
    fun Dispatch.getCommandLine(hwnd: Int): String {
        return Dispatch.call(this, "GetCommandLine", hwnd).string
    }

    /**
     * 函数简介: 根据指定的窗口句柄，来获取对应窗口句柄进程下的指定模块的基址
     * @param hwnd Int 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @param module String 模块名
     * @return Long 模块的基址
     */
    fun Dispatch.getModuleBaseAddr(hwnd: Int, module: String): Long {
        return Dispatch.call(this, "GetModuleBaseAddr", hwnd, module).long
    }

    /**
     * 函数简介: 根据指定的窗口句柄，来获取对应窗口句柄进程下的指定模块的基址
     * @param hwnd Int 指定搜索的窗口句柄或者进程ID(默认是窗口句柄，如果要指定为进程ID，需要调用SetMemoryHwndAsProcessId)
     * @param module String 模块名
     * @return Int 模块的大小
     */
    fun Dispatch.getModuleSize(hwnd: Int, module: String): Int {
        return Dispatch.call(this, "GetModuleSize", hwnd, module).int
    }

    /**
     * 根据指定的目标模块地址，获取目标窗口(进程)内的导出函数地址
     * @param hwnd Int
     * @param base_addr Int 目标模块地址，比如user32.dll的地址，可以通过GetModuleBaseAddr来获取
     * @param fun_name String 需要获取的导出函数名，比如"SetWindowTextA"
     * @return Int 获取的地址，如果失败返回0
     */
    fun Dispatch.getRemoteApiAddress(hwnd: Int, base_addr: Int, fun_name: String): Int {
        return Dispatch.call(this, "GetRemoteApiAddress", hwnd, base_addr, fun_name).int
    }

    /**
     * 函数简介: 根据指定pid打开进程，并返回进程句柄
     * @param pid Int 进程pid
     * @return Int 进程句柄, 可用于进程相关操作(读写操作等)，记得操作完成以后，自己调用CloseHandle关闭句柄
     */
    fun Dispatch.openProcess(pid: Int): Int {
        return Dispatch.call(this, "OpenProcess", pid).int
    }

    /**
     * 函数简介: 根据指定的PID，强制结束进程
     * @param pid Int 进程ID
     * @return Boolean
     */
    fun Dispatch.terminateProcess(pid: Int): Boolean {
        return Dispatch.call(this, "TerminateProcess", pid).int == 1
    }

    /*
    ********************************* 相关设置函数 *********************************
    */

    /**
     * 函数简介: 设置是否把所有内存接口函数中的窗口句柄当作进程ID,以支持直接以进程ID来使用内存接口
     * @param enable Boolean
     * @return Boolean
     */
    fun Dispatch.setMemoryHwndAsProcessId(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "SetMemoryHwndAsProcessId", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 设置是否把所有内存查找接口的结果保存入指定文件
     * @param file String 设置要保存的搜索结果文件名(如果为空字符串表示取消此功能)
     * @return Boolean
     */
    fun Dispatch.setMemoryFindResultToFile(file: String): Boolean {
        return Dispatch.call(this, "SetMemoryFindResultToFile", file).int == 1
    }

    /*
    ********************************* 其它内存函数 *********************************
    */

    /**
     * 函数简介: 在指定的窗口所在进程分配一段内存
     * @param hwnd Int
     * @param addr Int 预期的分配地址，如果是0表示自动分配，否则就尝试在此地址上分配内存
     * @param size Int 需要分配的内存大小
     * @param type Int 需要分配的内存大小
     * 0 : 可读可写可执行
     * 1 : 可读可执行，不可写
     * 2 : 可读可写,不可执行
     * @return Int 分配的内存地址，如果是0表示分配失败
     */
    fun Dispatch.virtualAllocEx(hwnd: Int, addr: Int, size: Int, type: Int): Int {
        return Dispatch.call(this, "VirtualAllocEx", hwnd, addr, size, type).int
    }

    /**
     * 函数简介: 释放用VirtualAllocEx分配的内存
     * @param hwnd Int
     * @param addr Int VirtualAllocEx返回的地址
     * @return Boolean
     */
    fun Dispatch.virtualFreeEx(hwnd: Int, addr: Int): Boolean {
        return Dispatch.call(this, "VirtualFreeEx", hwnd, addr).int == 1
    }

    /**
     * 函数简介: 修改指定的窗口所在进程的地址的读写属性，修改为可读可写可执行
     * @param hwnd Int
     * @param addr Int 需要修改的地址
     * @param size Int 需要修改的地址大小
     * @param type Int 修改的地址读写属性类型
     *  0 : 可读可写可执行,此时old_protect参数无效
     *  1 : 修改为old_protect指定的读写属性
     * @param old_protect Int 指定的读写属性
     * @return Int
     */
    fun Dispatch.virtualProtectEx(hwnd: Int, addr: Int, size: Int, type: Int, old_protect: Int): Int {
        return Dispatch.call(this, "VirtualProtectEx", hwnd, addr, size, type, old_protect).int
    }

    /**
     * 函数简介: 获取指定窗口，指定地址的内存属性
     * @param hwnd Int
     * @param addr Int 需要查询的地址
     * @param pmbi Int 这是一个地址,指向的内容是MEMORY_BASIC_INFORMATION32或者MEMORY_BASIC_INFORMATION64
     * 取决于要查询的进程是32位还是64位. 这个地址可以为0,忽略这个参数
     * @return String 查询的结果以字符串形式，内容是"BaseAddress,AllocationBase,
     * AllocationProtect,RegionSize,State,Protect,Type"，数值都是10进制表达
     */
    fun Dispatch.virtualQueryEx(hwnd: Int, addr: Int, pmbi: Int): String {
        return Dispatch.call(this, "VirtualQueryEx", hwnd, addr, pmbi).string
    }

}