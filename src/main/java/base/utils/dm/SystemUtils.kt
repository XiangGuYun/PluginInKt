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
}