package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 大漠窗口模块
 */
interface WindowUtils {

    /**
     * 函数简介: 根据指定进程名,枚举系统中符合条件的进程PID,并且按照进程打开顺序排序.
     * @param name String 进程名,比如qq.exe
     * @return String 返回所有匹配的进程PID,并按打开顺序排序,格式"pid1,pid2,pid3"
     * 示例:
     * pidString = dm.EnumProcess("notepad.exe")
     * pidArr = split(pidString , ",")
     */
    fun Dispatch.enumProcess(name: String): String {
        return Dispatch.call(this, "EnumProcess", name).string
    }

    /**
     * 函数简介: 根据指定条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     * @param parent Int 获得的窗口句柄是该窗口的子窗口的窗口句柄,取0时为获得桌面句柄
     * @param title String 窗口标题. 此参数是模糊匹配.
     * @param class_name String 窗口类名. 此参数是模糊匹配.
     * @param filter Int 取值定义如下
     * 1 : 匹配窗口标题,参数title有效
     * 2 : 匹配窗口类名,参数class_name有效.
     * 4 : 只匹配指定父窗口的第一层孩子窗口
     * 8 : 匹配父窗口为0的窗口,即顶级窗口
     * 16 : 匹配可见的窗口
     * 32 : 匹配出的窗口按照窗口打开顺序依次排列
     * 这些值可以相加,比如4+8+16就是类似于任务管理器中的窗口列表
     * @return String 返回所有匹配的窗口句柄字符串,格式"hwnd1,hwnd2,hwnd3"
     */
    fun Dispatch.enumWindow(parent: Int = 0, title: String?, class_name: String?, filter: Int = 1): String {
        return Dispatch.call(this, "EnumWindow", parent, title, class_name, filter).string
    }

    /**
     * 函数简介: 根据指定进程以及其它条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     * @param process_name String 进程映像名.比如(svchost.exe). 此参数是精确匹配,但不区分大小写.
     * @param title String 窗口标题. 此参数是模糊匹配.
     * @param class_name String 窗口类名. 此参数是模糊匹配.
     * @param filter Int 取值定义同enumWindow
     * @return String 返回所有匹配的窗口句柄字符串,格式"hwnd1,hwnd2,hwnd3"
     */
    fun Dispatch.enumWindowProcess(process_name: String , title: String?, class_name: String?, filter: Int = 1): String {
        return Dispatch.call(this, "EnumWindowProcess", process_name, title, class_name, filter).string
    }

    /**
     * 函数简介: 根据指定进程pid以及其它条件,枚举系统中符合条件的窗口,可以枚举到按键自带的无法枚举到的窗口
     * @param pid Int 进程pid.
     * @param title String 窗口标题. 此参数是模糊匹配.
     * @param class_name String 窗口类名. 此参数是模糊匹配.
     * @param filter Int 取值定义如下
     * 1 : 匹配窗口标题,参数title有效
     * 2 : 匹配窗口类名,参数class_name有效
     * 8 : 匹配父窗口为0的窗口,即顶级窗口
     * 16 : 匹配可见的窗口
     * 这些值可以相加,比如2+8+16
     * @return String 返回所有匹配的窗口句柄字符串,格式"hwnd1,hwnd2,hwnd3"
     */
    fun Dispatch.enumWindowByProcessId(pid: Int, title: String?, class_name: String?, filter: Int = 1): String {
        return Dispatch.call(this, "EnumWindowByProcessId", pid, title, class_name, filter).string
    }

    /**
     * 函数简介: 根据两组设定条件来枚举指定窗口.
     * @param spec1 String 查找串1. (内容取决于flag1的值)
     * @param flag1 Int 取值如下:
     * 0表示spec1的内容是标题
     * 1表示spec1的内容是程序名字. (比如notepad)
     * 2表示spec1的内容是类名
     * 3表示spec1的内容是程序路径.(不包含盘符,比如\windows\system32)
     * 4表示spec1的内容是父句柄.(十进制表达的串)
     * 5表示spec1的内容是父窗口标题
     * 6表示spec1的内容是父窗口类名
     * 7表示spec1的内容是顶级窗口句柄.(十进制表达的串)
     * 8表示spec1的内容是顶级窗口标题
     * 9表示spec1的内容是顶级窗口类名
     * @param type1 Int 0精确判断 1模糊判断
     * @param spec2 String 查找串2. (内容取决于flag2的值)
     * @param flag2 Int 取值如下:
     * 0表示spec2的内容是标题
     * 1表示spec2的内容是程序名字. (比如notepad)
     * 2表示spec2的内容是类名
     * 3表示spec2的内容是程序路径.(不包含盘符,比如\windows\system32)
     * 4表示spec2的内容是父句柄.(十进制表达的串)
     * 5表示spec2的内容是父窗口标题
     * 6表示spec2的内容是父窗口类名
     * 7表示spec2的内容是顶级窗口句柄.(十进制表达的串)
     * 8表示spec2的内容是顶级窗口标题
     * 9表示spec2的内容是顶级窗口类名
     * @param type2  Int 0精确判断 1模糊判断
     * @param sort  Int 0不排序 1对枚举出的窗口进行排序,按照窗口打开顺序.
     * @return String 返回所有匹配的窗口句柄字符串,格式"hwnd1,hwnd2,hwnd3"
     * 示例:hwnds = dm.EnumWindowSuper("记事本",0,1,"notepad",1,0,0)
     */
    fun Dispatch.enumWindowSuper(spec1: String , flag1: Int, type1: Int, spec2: String , flag2: Int, type2: Int, sort: Int): String {
        return Dispatch.call(this, "EnumWindowSuper", spec1, flag1, type1, spec2, flag2, type2, sort).string
    }

    /**
     * 函数简介: 查找符合类名或者标题名的顶层可见窗口
     * @param windowClass String 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param windowTitle String 窗口标题,如果为空，则匹配所有.这里的匹配是模糊匹配.
     * @return Int 整形数表示的窗口句柄，没找到返回0
     */
    fun Dispatch.findWindow(windowClass: String?, windowTitle: String?): Int {
        return Dispatch.call(this, "FindWindow", windowClass, windowTitle).int
    }

    /**
     * 函数简介: 根据指定的进程名字，来查找可见窗口.
     * @param process_name String 进程名. 比如(notepad.exe).这里是精确匹配,但不区分大小写.
     * @param windowClass String 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param windowTitle String 窗口标题,如果为空，则匹配所有.这里的匹配是模糊匹配.
     */
    fun Dispatch.findWindowByProcess(process_name:String , windowClass: String?, windowTitle: String?): Int {
        return Dispatch.call(this, "FindWindowByProcess", process_name, windowClass, windowTitle).int
    }

    /**
     * 函数简介: 根据指定的进程Id，来查找可见窗口.
     * @param process_id Int 进程id.
     * @param windowClass String 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param windowTitle String 窗口标题,如果为空，则匹配所有.这里的匹配是模糊匹配.
     */
    fun Dispatch.findWindowByProcessId(process_id:String , windowClass: String?, windowTitle: String?): Int {
        return Dispatch.call(this, "FindWindowByProcessId", process_id, windowClass, windowTitle).int
    }

    /**
     * 函数简介: 查找符合类名或者标题名的顶层可见窗口,如果指定了parent,则在parent的第一层子窗口中查找.
     * @param parent Int 父窗口句柄，如果为空，则匹配所有顶层窗口
     * @param windowClass String 窗口类名，如果为空，则匹配所有. 这里的匹配是模糊匹配.
     * @param windowTitle String 窗口标题,如果为空，则匹配所有. 这里的匹配是模糊匹配.
     */
    fun Dispatch.findWindowEx(parent: Int, windowClass: String?, windowTitle: String?): Int {
        return Dispatch.call(this, "FindWindowEx", parent, windowClass, windowTitle).int
    }

    /**
     * 函数简介: 根据两组设定条件来查找指定窗口.
     * 参数和返回值同enumWindowSuper
     */
    fun Dispatch.findWindowSuper(spec1: String , flag1: Int, type1: Int, spec2: String , flag2: Int, type2: Int, sort: Int): String {
        return Dispatch.call(this, "FindWindowSuper", spec1, flag1, type1, spec2, flag2, type2, sort).string
    }

    /**
     * 函数简介: 获取顶层活动窗口中具有输入焦点的窗口句柄
     */
    fun Dispatch.getForegroundFocus(): Int {
        return Dispatch.call(this, "GetForegroundFocus").int
    }

    /**
     * 函数简介: 获取顶层活动窗口,可以获取到按键自带插件无法获取到的句柄
     */
    fun Dispatch.getForegroundWindow(): Int {
        return Dispatch.call(this, "GetForegroundWindow").int
    }

    /**
     * 函数简介: 获取鼠标指向的可见窗口句柄,可以获取到按键自带的插件无法获取到的句柄
     */
    fun Dispatch.getMousePointWindow(): Int {
        return Dispatch.call(this, "GetMousePointWindow").int
    }

    /**
     * 函数简介: 获取给定坐标的可见窗口句柄,可以获取到按键自带的插件无法获取到的句柄
     * @param x Int 屏幕X坐标
     * @param y Int 屏幕Y坐标
     */
    fun Dispatch.getPointWindow(x:Int, y:Int): Int {
        return Dispatch.call(this, "GetPointWindow", x, y).int
    }

    /**
     * 函数简介: 根据指定的pid获取进程详细信息,(进程名,进程全路径,CPU占用率(百分比),内存占用量(字节))
     * @param pid Int 进程pid
     * @return String 格式"进程名|进程路径|cpu|内存"
     * 注: 有些时候有保护的时候，此函数返回内容会错误，那么此时可以尝试用memory保护盾来试试看.
     * 另外此接口调用会延迟1秒.
     */
    fun Dispatch.getProcessInfo(pid:Int): Int {
        return Dispatch.call(this, "GetProcessInfo", pid).int
    }

    /**
     * 函数简介: 获取特殊窗口
     * @param flag Int 取值定义如下
     * 0 : 获取桌面窗口
     * 1 : 获取任务栏窗口
     * @return Int以整型数表示的窗口句柄
     */
    fun Dispatch.getSpecialWindow(flag:Int): Int {
        return Dispatch.call(this, "GetSpecialWindow", flag).int
    }

    /**
     * 函数简介: 获取给定窗口相关的窗口句柄
     * @param hwnd Int 窗口句柄
     * @param flag Int 取值定义如下
     * 0 : 获取父窗口
     * 1 : 获取第一个儿子窗口
     * 2 : 获取First窗口
     * 3 : 获取Last窗口
     * 4 : 获取下一个窗口
     * 5 : 获取上一个窗口
     * 6 : 获取拥有者窗口
     * 7 : 获取顶层窗口
     * @return Int返回整型表示的窗口句柄
     */
    fun Dispatch.getWindow(hwnd:Int, flag:Int): Int {
        return Dispatch.call(this, "GetWindow", hwnd, flag).int
    }

    /**
     * 函数简介: 获取窗口的类名
     * @param hwnd Int 指定的窗口句柄
     */
    fun Dispatch.getWindowClass(hwnd: Int): String {
        return Dispatch.call(this, "GetWindowClass", hwnd).toString ()
    }

    /**
     * 函数简介: 获取指定窗口所在的进程ID
     * @param hwnd Int 指定的窗口句柄
     */
    fun Dispatch.getWindowProcessId(hwnd: Int): Int {
        return Dispatch.call(this, "GetWindowProcessId", hwnd).int
    }

    /**
     * 函数简介: 获取指定窗口所在的进程的exe文件全路径.
     * @param hwnd Int 窗口句柄
     */
    fun Dispatch.getWindowProcessPath(hwnd: Int): String {
        return Dispatch.call(this, "GetWindowProcessPath", hwnd).string
    }

    /**
     * 函数简介: 获取指定窗口的一些属性
     * @param hwnd Int 指定的窗口句柄
     * @param flag Int 取值定义如下
     * 0 : 判断窗口是否存在
     * 1 : 判断窗口是否处于激活
     * 2 : 判断窗口是否可见
     * 3 : 判断窗口是否最小化
     * 4 : 判断窗口是否最大化
     * 5 : 判断窗口是否置顶
     * 6 : 判断窗口是否无响应
     * 7 : 判断窗口是否可用(灰色为不可用)
     * 8 : 另外的方式判断窗口是否无响应,如果6无效可以尝试这个
     * 9 : 判断窗口所在进程是不是64位
     */
    fun Dispatch.getWindowState(hwnd: Int, flag:Int): Boolean {
        return Dispatch.call(this, "GetWindowState", hwnd, flag).int == 1
    }

    /**
     * 函数简介: 获取窗口标题
     * @param hwnd Int 指定的窗口句柄
     */
    fun Dispatch.getWindowTitle(hwnd: Int): String {
        return Dispatch.call(this, "GetWindowTitle", hwnd).toString ()
    }

    /**
     * 函数简介: 移动指定窗口到指定位置
     * @param hwnd Int 指定的窗口句柄
     * @param x Int X坐标
     * @param y Int Y坐标
     */
    fun Dispatch.moveWindow(hwnd: Int, x:Int, y:Int): Boolean {
        return Dispatch.call(this, "MoveWindow", hwnd, x, y).int == 1
    }

    /**
     * 函数简介: 向指定窗口发送粘贴命令，把剪贴板的内容发送到目标窗口.
     * @param hwnd Int 指定的窗口句柄. 如果为0,则对当前激活的窗口发送.
     * 注:剪贴板是公共资源，多个线程同时设置剪贴板时,会产生冲突，必须用互斥信号保护.
     */
    fun Dispatch.sendPaste(hwnd: Int): Boolean {
        return Dispatch.call(this, "SendPaste", hwnd).int == 1
    }

    /**
     * 函数简介: 向指定窗口发送文本数据
     * @param hwnd Int 指定的窗口句柄. 如果为0,则对当前激活的窗口发送.
     * @param str String 发送的文本数据
     */
    fun Dispatch.sendString(hwnd: Int, str: String): Boolean {
        return Dispatch.call(this, "SendString", hwnd, str).int == 1
    }

    /**
     * 函数简介: 向指定窗口发送文本数据
     * 注: 此接口为老的SendString ，如果新的SendString 不能输入，可以尝试此接口.
     */
    fun Dispatch.sendString2(wh: Int, str: String): Boolean {
        return Dispatch.call(this, "SendString2", wh, str).int == 1
    }

    /**
     * 函数简介: 向绑定的窗口发送文本数据.必须配合dx.public.input.ime属性.
     * @param str String 发送的文本数据
     * 示例:
     * dm_ret = dm.BindWindowEx(hwnd,"normal","normal","normal","dx.public.input.ime",0)
     * dm.SendStringIme "我是来测试的"
     */
    fun Dispatch.sendStringIme(str: String): Boolean {
        return Dispatch.call(this, "SendStringIme", str).int == 1
    }

    /**
     * 函数简介: 利用真实的输入法，对指定的窗口输入文字.
     * @param hwnd Int 窗口句柄
     * @param str String 发送的文本数据
     * @param mode Int 取值意义如下:
     * 0 : 向hwnd的窗口输入文字(前提是必须先用模式200安装了输入法)
     * 1 : 同模式0,如果由于保护无效，可以尝试此模式.(前提是必须先用模式200安装了输入法)
     * 2 : 同模式0,如果由于保护无效，可以尝试此模式. (前提是必须先用模式200安装了输入法)
     * 200 : 向系统中安装输入法,多次调用没问题. 全局只用安装一次.
     * 300 : 卸载系统中的输入法. 全局只用卸载一次. 多次调用没关系.
     * 示例:
     * If dm.SendStringIme2(hwnd,"",200) = 1 then
     * dm.SendStringIme2 hwnd,"我是来测试的",0
     * dm.SendStringIme2 hwnd,"abc",0
     * dm.SendStringIme2 hwnd,"123",0
     * dm.SendStringIme2 hwnd,"",300
     * end if
     * 注: 如果要同时对此窗口进行绑定，并且绑定的模式是1 3 5 7 101 103，那么您必须要在绑定之前,先执行加载输入法的操作.
     * 否则会造成绑定失败!. 卸载时，没有限制.
     * 还有，在后台输入时，如果目标窗口有判断是否在激活状态才接受输入文字,那么可以配合绑定窗口中的假激活属性来保证文字正常输入.
     * 诸如此类. 基本上用这个没有输入不了的文字.
     * 比如
     * BindWindow hwnd,"normal","normal","normal","dx.public.active.api|dx.public.active.message",0
     * dm.SendStringIme2 hwnd,"哈哈",0
     */
    fun Dispatch.sendStringIme2(hwnd:Int, str: String , mode:Int): Boolean {
        return Dispatch.call(this, "SendStringIme2", hwnd, str).int == 1
    }

    /**
     * 函数简介: 设置窗口客户区域的宽度和高度
     */
    fun Dispatch.setClientSize(hwnd: Int, width: Int, height: Int): Boolean {
        val result = Dispatch.call(this, "SetClientSize", hwnd, width, height).int
        return result == 1
    }

    /**
     * 函数简介: 设置窗口的大小
     */
    fun Dispatch.setWindowSize(hwnd: Int, width: Int, height: Int): Boolean {
        val result = Dispatch.call(this, "SetWindowSize", hwnd, width, height).int
        return result == 1
    }

    /**
     * 函数简介: 设置窗口的状态
     * @param hwnd Int 指定的窗口句柄
     * @param flag Int 取值定义如下
     * 0 : 关闭指定窗口
     * 1 : 激活指定窗口
     * 2 : 最小化指定窗口,但不激活
     * 3 : 最小化指定窗口,并释放内存,但同时也会激活窗口.(释放内存可以考虑用FreeProcessMemory函数)
     * 4 : 最大化指定窗口,同时激活窗口
     * 5 : 恢复指定窗口 ,但不激活
     * 6 : 隐藏指定窗口
     * 7 : 显示指定窗口
     * 8 : 置顶指定窗口
     * 9 : 取消置顶指定窗口
     * 10 : 禁止指定窗口
     * 11 : 取消禁止指定窗口
     * 12 : 恢复并激活指定窗口
     * 13 : 强制结束窗口所在进程
     * 14 : 闪烁指定的窗口
     * 15 : 使指定的窗口获取输入焦点
     */
    fun Dispatch.setWindowState(hwnd: Int, flag: Int): Boolean {
        val result = Dispatch.call(this, "SetWindowState", hwnd, flag).int
        return result == 1
    }

    /**
     * 函数简介: 设置窗口的标题
     * @param hwnd Int 指定的窗口句柄
     * @param titie String 标题
     */
    fun Dispatch.setWindowText(wh: Int, title: String): Boolean {
        return Dispatch.call(this, "SetWindowText", wh, title).int == 1
    }

    /**
     * 函数简介: 设置窗口的透明度
     * @param hwnd Int 指定的窗口句柄
     * @param trans Int 透明度取值(0-255) 越小透明度越大 0为完全透明(不可见) 255为完全显示(不透明)
     */
    fun Dispatch.setWindowTransparent(wh: Int, trans: Int): Boolean {
        return Dispatch.call(this, "SetWindowTransparent", wh, trans).int == 1
    }

}