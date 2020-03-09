package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 大漠系统模块
 */
interface SystemUtils {

    /**
     * 函数简介: 蜂鸣器，用于提示或警告
     * @param freq Int 频率
     * @param duration Int 时长(ms)
     * @return Boolean 是否成功
     * 示例:dm.Beep 1000,1000
     */
    fun Dispatch.beep(freq: Int, duration: Int): Boolean {
        return Dispatch.call(this, "Beep", freq, duration).int == 1
    }

    /**
     * 函数简介: 退出系统(注销 重启 关机)
     * @param type Int 取值为以下类型
     * 0 : 注销系统
     * 1 : 关机
     * 2 : 重新启动
     */
    fun Dispatch.exitOs(type: Int): Boolean {
        return Dispatch.call(this, "ExitOs", type).int == 1
    }

    /**
     * 函数简介: 运行指定的应用程序
     * @param appPath String 指定的可执行程序全路径
     * @param mode Int 取值如下
     * 0 : 普通模式
     * 1 : 加强模式
     * 示例:
     * dm.RunApp "c:\windows\notepad.exe",0
     * dm.RunApp "notepad",1
     */
    fun Dispatch.runApp(appPath: String, mode: Int): Boolean {
        return Dispatch.call(this, "RunApp", appPath, mode).int == 1
    }

    /**
     * 函数简介: 设置剪贴板的内容
     * @param value String 以字符串表示的剪贴板内容
     */
    fun Dispatch.setClipboard(value: String): Boolean {
        return Dispatch.call(this, "SetClipboard", value).int == 1
    }

    /**
     * 函数简介: 获取剪贴板的内容
     * @return String 以字符串表示的剪贴板内容
     */
    fun Dispatch.getClipboard(): String {
        return Dispatch.call(this, "GetClipboard").string
    }

    /**
     * 函数简介: 得到系统的路径
     * @param type Int
     *  0 : 获取当前路径
     *  1 : 获取系统路径(system32路径)
     *  2 : 获取windows路径(windows所在路径)
     *  3 : 获取临时目录路径(temp)
     *  4 : 获取当前进程(exe)所在的路径
     * @return String
     */
    fun Dispatch.getDir(type: Int): String {
        return Dispatch.call(this, "GetDir", type).string
    }

    /**
     * 函数简介: 检测当前系统是否开启了UAC
     * @receiver Dispatch
     * @return Boolean
     */
    fun Dispatch.checkUAC(): Boolean {
        return Dispatch.call(this, "CheckUAC").int == 1
    }

    /**
     * 函数简介: 设置当前系统的UAC
     * @receiver Dispatch
     * @param enable Boolean
     * @return Boolean
     */
    fun Dispatch.setUAC(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "SetUAC").int == 1
    }

    /**
     * 函数简介: 获取当前系统从开机到现在所经历过的时间，单位是毫秒
     * @receiver Dispatch
     * @return Long
     */
    fun Dispatch.getTime(): Long {
        return Dispatch.call(this, "GetTime").long
    }

}