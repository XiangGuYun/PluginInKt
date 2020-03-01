package base.utils.dm

import com.jacob.activeX.ActiveXComponent
import com.jacob.com.Dispatch

interface BasicSettingUtils {

    /**
     * 函数简介:注册大漠，从而使用插件的高级功能，推荐使用此函数
     * @param reg_code String 注册码. (从大漠插件后台获取)
     * @param ver_info String 版本附加信息. 可以在后台详细信息查看. 可以任意填写. 可留空
     * @return Int
     * -1 : 无法连接网络,(可能防火墙拦截,如果可以正常访问大漠插件网站，那就可以肯定是被防火墙拦截)
     * -2 : 进程没有以管理员方式运行. (出现在win7 win8 vista 2008.建议关闭uac)
     *  0 : 失败 (未知错误)
     *  1 : 成功
     *  2 : 余额不足
     *  3 : 绑定了本机器，但是账户余额不足50元.
     *  4 : 注册码错误
     *  5 : 你的机器或者IP在黑名单列表中或者不在白名单列表中.
     *  6 : 非法使用插件.
     *  7 : 你的帐号因为非法使用被封禁. （如果是在虚拟机中使用插件，必须使用Reg或者RegEx，
     *     不能使用RegNoMac或者RegExNoMac,否则可能会造成封号，或者封禁机器）
     *  8 : ver_info不在你设置的附加白名单中.
     * 77 : 机器码或者IP因为非法使用，而被封禁. （如果是在虚拟机中使用插件，
     *      必须使用Reg或者RegEx，不能使用RegNoMac或者RegExNoMac,否则可能会造成封号，或者封禁机器）
     *      封禁是全局的，如果使用了别人的软件导致77，也一样会导致所有注册码均无法注册。解决办法是更换IP，更换MAC
     * -8 : 版本附加信息长度超过了20
     * -9 : 版本附加信息里包含了非法字母
     */
    fun Dispatch.reg(): Int {
        return Dispatch.call(this, "Reg",
                "yida94563887ce815854f20ab8c0926f99a987b4cea",
                "uf3am").int
    }

    /**
     * 函数原型:获取当前大漠的版本
     */
    fun getDmVersion(): String {
        return ActiveXComponent("dm.dmsoft").invoke("Ver").string
    }

    /**
     * 函数简介:
     * 设置全局路径,设置了此路径后,所有接口调用中,相关的文件都相对于此路径. 比如图片,字库等.
     * @param path 字符串: 路径,可以是相对路径,也可以是绝对路径
     * 示例:
     * // 以下代码把全局路径设置到了c盘根目录
     * dm_ret = dm.SetPath("c:\")
     * // 如下是把全局路径设置到了相对于当前exe所在的路径
     * dm.SetPath ".\MyData"
     * // 以上，如果exe在c:\test\a.exe 那么，就相当于把路径设置到了c:\test\MyData
     */
    fun Dispatch.setPath(path: String): Boolean {
        return Dispatch.call(this, "SetPath", path).int == 1
    }

}