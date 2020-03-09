package base.utils.dm

import com.jacob.activeX.ActiveXComponent
import com.jacob.com.Dispatch
import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * 大漠基本设置模块
 */
interface BasicSettingUtils {

    /**
     * 函数简介: 注册大漠，从而使用插件的高级功能，推荐使用此函数
     * @param reg_code String 注册码(从大漠插件后台获取)
     * @param ver_info String 版本附加信息。可以在后台详细信息查看，可以任意填写，可留空
     * @return Int
     * -1 : 无法连接网络(可能防火墙拦截,如果可以正常访问大漠插件网站，那就可以肯定是被防火墙拦截)
     * -2 : 进程没有以管理员方式运行(出现在win7、win8、vista2008，建议关闭uac)
     *  0 : 失败(未知错误)
     *  1 : 成功
     *  2 : 余额不足
     *  3 : 绑定了本机器，但是账户余额不足50元
     *  4 : 注册码错误
     *  5 : 你的机器或者IP在黑名单列表中或者不在白名单列表中
     *  6 : 非法使用插件
     *  7 : 你的帐号因为非法使用被封禁(如果是在虚拟机中使用插件，必须使用Reg或者RegEx，
     *     不能使用RegNoMac或者RegExNoMac，否则可能会造成封号，或者封禁机器）
     *  8 : ver_info不在你设置的附加白名单中
     * 77 : 机器码或者IP因为非法使用，而被封禁(如果是在虚拟机中使用插件，
     *      必须使用Reg或者RegEx，不能使用RegNoMac或者RegExNoMac，否则可能会造成封号，或者封禁机器)
     *      封禁是全局的，如果使用了别人的软件导致77，也一样会导致所有注册码均无法注册。解决办法是更换IP，更换MAC
     * -8 : 版本附加信息长度超过了20
     * -9 : 版本附加信息里包含了非法字母
     */
    fun Dispatch.reg(reg_code: String = "yida94563887ce815854f20ab8c0926f99a987b4cea",
                     ver_info: String = "uf3am"): Int {
        return Dispatch.call(this, "Reg", reg_code, ver_info).int
    }

    /**
     * 函数原型:获取当前大漠的版本
     */
    fun getDmVersion(): String {
        return ActiveXComponent("dm.dmsoft").invoke("Ver").string
    }

    /**
     * 函数简介: 设置全局路径，设置了此路径后，所有接口调用中，相关的文件都相对于此路径，比如图片、字库等
     * @param path String 路径，可以是相对路径，也可以是绝对路径
     * 示例:
     * //以下代码把全局路径设置到了c盘根目录
     * dm_ret = dm.SetPath("c:\")
     * //如下是把全局路径设置到了相对于当前exe所在的路径
     * dm.SetPath ".\MyData"
     * //以上，如果exe在c:\test\a.exe 那么，就相当于把路径设置到了c:\test\MyData
     */
    fun Dispatch.setPath(path: String): Boolean {
        return Dispatch.call(this, "SetPath", path).int == 1
    }

    /**
     * 函数简介: 获取注册在系统中的dm.dll的路径
     * @receiver Dispatch
     * @return String
     */
    fun Dispatch.getBasePath(): String{
        return Dispatch.call(this, "GetBasePath").string
    }

    /**
     * 函数简介: 返回当前大漠对象的ID值，这个值对于每个对象是唯一存在的，可以用来判定两个大漠对象是否一致
     * @receiver Dispatch
     * @return Int
     */
    fun Dispatch.getId():Int{
        return Dispatch.call(this, "GetId").int
    }

    /**
     * 函数简介: 返回当前进程已经创建的dm对象个数
     * @receiver Dispatch
     * @return Int
     */
    fun Dispatch.getDmCount():Int{
        return Dispatch.call(this, "GetDmCount").int
    }

    /**
     * 函数简介: 返回当前设置的全局路径
     * @receiver Dispatch
     * @return String
     */
    fun Dispatch.getPath(): String{
        return Dispatch.call(this, "GetPath").string
    }

    /**
     * 函数简介: 设定图色的获取方式，默认是显示器或者后台窗口(具体参考BindWindow)
     * @param mode 字符串: 图色输入模式取值有以下几种
     * 1. "screen": 这个是默认的模式，表示使用显示器或者后台窗口
     * 2. "pic:file": 指定输入模式为指定的图片。如果使用了这个模式，则所有和图色相关的函数
     * 均视为对此图片进行处理，比如文字识别查找图片、颜色等等一切图色函数
     * 需要注意的是，设定以后，此图片就已经加入了缓冲，如果更改了源图片内容，那么需要释放此缓冲，重新设置
     * 3. "mem: addr,size": 指定输入模式为指定的图片，此图片在内存当中。addr为图像内存地址，size为图像内存大小
     * 如果使用了这个模式，则所有和图色相关的函数,均视为对此图片进行处理
     * 比如文字识别 查找图片 颜色 等等一切图色函数
     * 代码示例:
     * <code>
     *     // 设定为默认的模式
     *     dm_ret = dm.SetDisplayInput("screen")
     *     // 设定为图片模式 图片采用相对路径模式 相对于SetPath的路径
     *     dm_ret = dm.SetDisplayInput("pic:test.bmp")
     *     // 设为图片模式 图片采用绝对路径模式
     *     dm_ret = dm.SetDisplayInput("pic:d:\test\test.bmp")
     *     // 设为图片模式 但是每次设置前 先清除缓冲
     *     dm_ret = dm.FreePic("test.bmp")
     *     dm_ret = dm.SetDisplayInput("pic:test.bmp")
     *     // 设置为图片模式,图片从内存中获取
     *     dm_ret = dm.SetDisplayInput("mem:1230434,884")
     * </code>
     * @return Boolean
     */
    fun Dispatch.setDisplayInput(mode: Int): Boolean {
        return Dispatch.call(this, "SetDisplayInput", mode).int == 1
    }

    /**
     * 函数简介: 设置是否对前台图色进行加速(默认是关闭)，对于不绑定，或者绑定图色为normal生效，仅对WIN8以上系统有效
     * @param enable Boolean
     * @return Boolean
     * 注: WIN8以上系统，由于AERO的开启，导致前台图色速度很慢，使用此接口可以显著提速
     * WIN7系统无法使用，只能通过关闭aero来对前台图色提速
     * 每个进程,最多只能有一个对象开启此加速接口,如果要用开启别的对象的加速，那么要先关闭之前开启的
     * 并且开启此接口后,仅能对主显示器的屏幕进行截图，分屏的显示器上的内容无法截图
     * 另外需要注意: 开启此接口后，程序CPU会有一定上升，因为这个方法是以牺牲CPU性能来提升速度的
     */
    fun Dispatch.speedNormalGraphic(enable: Boolean): Boolean {
        return Dispatch.call(this, "SpeedNormalGraphic", if(enable)1 else 0).int == 1
    }

}