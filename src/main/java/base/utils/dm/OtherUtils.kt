package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 杂项
 */
interface OtherUtils {

    /**
     * 函数简介: 激活指定窗口所在进程的输入法
     * @param hwnd Int
     * @param input_method String 输入法名字
     * 具体输入法名字对应表查看注册表中以下位置:
     * HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Keyboard Layouts
     * 下面的每一项下的Layout Text的值就是输入法名字，比如 "中文 - QQ拼音输入法"
     * @return Boolean 激活是否成功
     */
    fun Dispatch.activeInputMethod(hwnd: Int, input_method: String): Boolean {
        return Dispatch.call(this, "ActiveInputMethod", hwnd, input_method).int == 1
    }

    /**
     * 函数简介: 检测指定窗口所在线程输入法是否开启
     * @param hwnd Int
     * @param input_method String 输入法名字
     * 具体输入法名字对应表查看注册表中以下位置:
     * HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Keyboard Layouts
     * 下面的每一项下的Layout Text的值就是输入法名字，比如 "中文 - QQ拼音输入法"
     * @return Boolean true开启 false关闭
     */
    fun Dispatch.checkInputMethod(hwnd: Int, input_method: String): Boolean {
        return Dispatch.call(this, "ActiveInputMethod", hwnd, input_method).int == 1
    }

    /**
     * 函数简介: 执行指定的CMD指令,并返回cmd的输出结果
     * @param cmd String 需要执行的CMD指令(比如"dir")
     * @param current_dir String 执行此cmd命令时所在目录。如果为空，表示使用当前目录. 比如""或者"c:"
     * @param time_out Int 超时设置，单位是毫秒。0表示一直等待，大于0表示等待指定的时间后强制结束，防止卡死
     * @return Boolean 命令行执行结果，返回空字符串表示执行失败
     */
    fun Dispatch.executeCmd(cmd:String,current_dir:String,time_out:Int): String {
        return Dispatch.call(this, "ExecuteCmd", cmd,current_dir,time_out).string
    }

    /**
     * 函数简介: 检测系统中是否安装了指定输入法
     * @param input_method String 输入法名字
     * 具体输入法名字对应表查看注册表中以下位置:
     * HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Keyboard Layouts
     * 下面的每一项下的Layout Text的值就是输入法名字，比如 "中文 - QQ拼音输入法"
     * @return Boolean true已安装 false未安装
     */
    fun Dispatch.findInputMethod(input_method: String): Boolean {
        return Dispatch.call(this, "FindInputMethod", input_method).int == 1
    }

    /**
     * 函数简介: 强制降低对象的引用计数。此接口为高级接口，一般使用在高级语言，比如E vc等
     * @return Boolean
     */
    fun Dispatch.releaseRef(): Boolean {
        return Dispatch.call(this, "ReleaseRef").int == 1
    }

    /**
     * 函数简介: 设置当前对象的退出线程标记，之后除了调用此接口的线程之外，调用此对象的任何接口的线程会被强制退出
     * 此接口为高级接口，一般用在高级语言,比如e vc等
     * @param enable Boolean true开启 false关闭
     * @return Boolean 操作是否成功
     * 一般我们在写多线程程序时，如何正确的结束线程是个难题。脚本语言一般没这种烦恼，但高级语言比如E vc等就很麻烦，
     * 一般来说，让线程自然的结束，那是最好的结果。但是事实上，高级语言中很难做到，因为调用的函数是一层套一层，很难返回，
     * 所以，我们退而求其次，让线程自己调用退出，这样虽然也有一定的资源泄漏(主要是线程中创建的局部变量，比如类对象等)，但总比强制结束线程要好的多
     * 所以，我们这个接口的目的也很明显，设置以后，除了调用线程之外的线程，如果调用到插件，那么线程就自己退出了
     */
    fun Dispatch.setExitThread(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "SetExitThread", if(enable)1 else 0).int == 1
    }

}