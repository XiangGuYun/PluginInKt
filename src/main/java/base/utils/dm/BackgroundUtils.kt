package base.utils.dm

import com.jacob.com.Dispatch

/**
 * 大漠后台模块
 */
interface BackgroundUtils {

    enum class Display {
        NORMAL,
        GDI,
        GDI2
    }

    enum class Mouse {
        NORMAL,
        WINDOWS,
        WINDOWS2,
        WINDOWS3
    }

    enum class Keyboard {
        NORMAL,
        WINDOWS
    }

    /**
     * 函数简介: 绑定指定的窗口,并指定这个窗口的屏幕颜色获取方式,鼠标仿真模式,键盘仿真模式,以及模式设定
     * @receiver Dispatch
     * @param hwnd Int 指定的窗口句柄
     *
     * @param display String 屏幕颜色获取方式
     * "normal" : 正常模式,平常我们用的前台截屏模式
     * "gdi" : gdi模式，用于窗口采用GDI方式刷新时，此模式占用CPU较大，参考SetAero(win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试)
     * "gdi2" : gdi2模式，此模式兼容性较强，但是速度比gdi模式要慢许多，如果gdi模式发现后台不刷新时，可以考虑用gdi2模式
     * "dx2" : dx2模式，用于窗口采用dx模式刷新，如果dx方式会出现窗口所在进程崩溃的状况，可以考虑采用这种。
     * 采用这种方式要保证窗口有一部分在屏幕外。win7、win8或者vista不需要移动也可后台。
     * 此模式占用CPU较大。参考SetAero( win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试)
     * "dx3" : dx3模式，同dx2模式,但是如果发现有些窗口后台不刷新时，可以考虑用dx3模式,此模式比dx2模式慢许多。
     * 此模式占用CPU较大. 参考SetAero(win10以上系统使用此模式，如果截图失败，尝试把目标程序重新开启再试试)
     * "dx" : dx模式，等同于BindWindowEx中，display设置的"dx.graphic.2d|dx.graphic.3d"，具体参考BindWindowEx
     *
     * @param mouse String 鼠标仿真模式
     * "normal" : 正常模式,平常我们用的前台鼠标模式
     * "windows": Windows模式,采取模拟windows消息方式 同按键自带后台插件.
     * "windows2": Windows2 模式,采取模拟windows消息方式(锁定鼠标位置) 此模式等同于BindWindowEx中的mouse为以下组合
     * "dx.mouse.position.lock.api|dx.mouse.position.lock.message|dx.mouse.state.message"
     * "windows3": Windows3模式，采取模拟windows消息方式,可以支持有多个子窗口的窗口后台.
     * "dx": dx模式,采用模拟dx后台鼠标模式,这种方式会锁定鼠标输入.有些窗口在此模式下绑定时，
     * 需要先激活窗口再绑定(或者绑定以后激活)，否则可能会出现绑定后鼠标无效的情况.此模式等同于BindWindowEx中的mouse为以下组合:
     * "dx.public.active.api|dx.public.active.message|dx.mouse.position.lock.api|dx.mouse.position.lock.message|
     * dx.mouse.state.api|dx.mouse.state.message|dx.mouse.api|dx.mouse.focus.input.api|dx.mouse.focus.input.message|
     * dx.mouse.clip.lock.api|dx.mouse.input.lock.api|dx.mouse.cursor"
     * "dx2": dx2模式,这种方式类似于dx模式,但是不会锁定外部鼠标输入.
     * 有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后手动激活)，否则可能会出现绑定后鼠标无效的情况
     * 此模式等同于BindWindowEx中的mouse为以下组合:
     * "dx.public.active.api|dx.public.active.message|dx.mouse.position.lock.api|dx.mouse.state.api|
     * dx.mouse.api|dx.mouse.focus.input.api|dx.mouse.focus.input.message|dx.mouse.clip.lock.api|
     * dx.mouse.input.lock.api| dx.mouse.cursor"

     * @param keypad String 键盘仿真模式
     * "normal" : 正常模式(平常我们用的前台键盘模式)
     * "windows": Windows模式，采取模拟windows消息方式(同按键的后台插件)
     * "dx": dx模式，采用模拟dx后台键盘模式。有些窗口在此模式下绑定时，需要先激活窗口再绑定(或者绑定以后激活)，
     * 否则可能会出现绑定后键盘无效的情况。此模式等同于BindWindowEx中的keypad为以下组合:
     * "dx.public.active.api|dx.public.active.message|dx.keypad.state.api|dx.keypad.api|dx.keypad.input.lock.api"

     * @param mode Int 模式
     *  0 : 推荐模式此模式比较通用，而且后台效果是最好的
     *  2 : 同模式0,如果模式0有崩溃问题，可以尝试此模式
     *  注意0和2模式，当主绑定(第一个绑定同个窗口的对象)绑定成功后，那么调用主绑定的线程必须一致维持，否则线程一旦推出对应的绑定也会消失
     *  101 : 超级绑定模式。可隐藏目标进程中的dm.dll，避免被恶意检测。效果要比dx.public.hide.dll好，推荐使用
     *  103 : 同模式101，如果模式101有崩溃问题，可以尝试此模式
     *  11 : 需要加载驱动，适合一些特殊的窗口。如果前面的无法绑定，可以尝试此模式，此模式不支持32位系统
     *  13 : 需要加载驱动，适合一些特殊的窗口。如果前面的无法绑定，可以尝试此模式，此模式不支持32位系统
     *  要注意的是: 模式101、103在大部分窗口下绑定都没问题。但也有少数特殊的窗口，比如有很多子窗口的窗口，
     *  对于这种窗口，在绑定时，一定要把鼠标指向一个可以输入文字的窗口，比如一个文本框，最好能激活这个文本框，这样可以保证绑定的成功
     * @return Boolean 绑定是否成功
     */
    fun Dispatch.bindWindow(hwnd: Int, display: String, mouse: String, keypad: String, mode: Int): Boolean {
        return Dispatch.call(this, hwnd, display, mouse, keypad, mode).int == 1
    }

    /**
     * 函数简介: 绑定指定的窗口，并指定这个窗口的屏幕颜色获取方式、鼠标仿真模式、键盘仿真模式
     * 参数也已由大漠后台绑定工具调试生成，各参数的介绍详见大漠文档
     * @receiver Dispatch
     * @param hwnd Int
     * @param display String
     * @param mouse String
     * @param keypad String
     * @param public String
     * @param mode Int
     * @return Boolean 绑定是否成功
     */
    fun Dispatch.bindWindowEx(hwnd: Int, display: String, mouse: String, keypad: String, public: String, mode: Int): Boolean {
        return Dispatch.call(this, hwnd, display, mouse, keypad, public, mode).int == 1
    }

    /**
     * 函数简介: 绑定窗口，本函数不适用于dx模式
     */
    fun Dispatch.bindWindowLite(hwnd: Int, display: Display, mouse: Mouse, keyboard: Keyboard): Boolean {
        return Dispatch.call(this, "BindWindowEx", hwnd,
                when (display) {
                    Display.NORMAL -> "normal"
                    Display.GDI -> "gdi"
                    else -> "gdi2"
                },
                when (mouse) {
                    Mouse.NORMAL -> "normal"
                    Mouse.WINDOWS -> "windows"
                    Mouse.WINDOWS2 -> "windows2"
                    else -> "windows3"
                },
                when (keyboard) {
                    Keyboard.NORMAL -> "normal"
                    Keyboard.WINDOWS -> "windows"
                    else -> "dx.public.active.api|dx.public.active.message| dx.keypad.state.api|dx.keypad.api|dx.keypad.input.lock.api"
                },
                null,
                0
        ).int == 1
    }

    /**
     * 函数简介: 降低目标窗口所在进程的CPU占用
     * @receiver Dispatch
     * @param rate Int 取值范围大于等于0，取值为0时表示关闭CPU优化(这个值越大表示降低CPU效果越好)
     * @return Boolean 是否成功
     * 注意: 此接口必须在绑定窗口成功以后调用，而且必须保证目标窗口可以支持dx.graphic.3d或者dx.graphic.3d.8
     * 或者dx.graphic.2d或者dx.graphic.2d.2或者dx.graphic.opengl或者dx.graphic.opengl.esv2方式截图，
     * 或者使用dx.public.down.cpu，否则降低CPU无效！！
     * 因为降低CPU是通过降低窗口刷新速度或者在系统消息循环增加延时来实现，所以注意开启此功能以后会导致窗口刷新速度变慢！
     */
    fun Dispatch.downCpu(rate: Int): Boolean {
        return Dispatch.call(this, "DownCpu", rate).int == 1
    }

    /**
     * 函数简介: 设置是否暂时关闭或者开启后台功能，默认是开启
     * 一般用在前台切换，或者脚本暂停和恢复时，可以让用户操作窗口
     * @receiver Dispatch
     * @param enable Int
     *  0: 全部关闭(图色键鼠都关闭，也就是说图色键鼠都是前台，但是如果有指定dx.public.active.message时，在窗口前后台切换时，这个属性会失效)
     * -1: 只关闭图色(也就是说图色是normal前台，键鼠不变)
     *  1: 开启(恢复原始状态)
     *  5: 同0，也是全部关闭，但是这个模式下，就算窗口在前后台切换时，属性dx.public.active.message的效果也一样不会失效
     * @return Boolean 是否成功
     * 注意: 切换到前台以后，相当于dm_ret = dm.BindWindow(hwnd,"normal","normal","normal",0)，图色键鼠全部是前台
     * 如果你经常有频繁切换后台和前台的操作，推荐使用这个函数
     * 同时要注意，如果有多个对象绑定了同个窗口，其中任何一个对象禁止了后台，那么其他对象后台也同样失效
     */
    fun Dispatch.enableBind(enable: Int): Boolean {
        return Dispatch.call(this, "EnableBind", enable).int == 1
    }

    /**
     * 函数简介: 设置是否开启后台假激活功能(默认是关闭，一般用不到，除非有人有特殊需求)
     * @receiver Dispatch
     * @param enable Boolean
     * @return Boolean
     */
    fun Dispatch.enableFakeActive(enable: Boolean): Boolean {
        return Dispatch.call(this, "EnableFakeActive", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 设置是否关闭绑定窗口所在进程的输入法
     * @receiver Dispatch
     * @param enable Boolean
     * @return Boolean
     */
    fun Dispatch.enableIme(enable: Boolean): Boolean {
        return Dispatch.call(this, "EnableIme", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 键盘消息采用同步发送模式(默认异步)
     * @receiver Dispatch
     * @param enable Boolean true开启 false禁止
     * @param time_out Int 单位是毫秒，表示同步等待的最大时间
     * @return Boolean 是否成功
     * 注意: 此接口必须在绑定之后才能调用。
     * 有些时候，如果是异步发送，如果发送动作太快，中间没有延时，有可能下个动作会影响前面的
     * 而用同步就没有这个担心
     */
    fun Dispatch.enableKeypadSync(enable: Boolean, time_out: Int): Boolean {
        return Dispatch.call(this, "EnableKeypadSync", if (enable) 1 else 0, time_out).int == 1
    }

    /**
     * 函数简介: 鼠标消息采用同步发送模式(默认异步)
     * @receiver Dispatch
     * @param enable Boolean true开启 false禁止
     * @param time_out Int 单位是毫秒，表示同步等待的最大时间
     * @return Boolean 是否成功
     * 注意: 此接口必须在绑定之后才能调用。
     * 有些时候，如果是异步发送，如果发送动作太快，中间没有延时，有可能下个动作会影响前面的
     * 而用同步就没有这个担心
     */
    fun Dispatch.enableMouseSync(enable: Boolean, time_out: Int): Boolean {
        return Dispatch.call(this, "EnableMouseSync", if (enable) 1 else 0, time_out).int == 1
    }

    /**
     * 函数简介: 键盘动作模拟真实操作,点击延时随机
     * @receiver Dispatch
     * @param enable Boolean true开启模拟 false关闭模拟
     * @return Boolean 开启或关闭是否成功
     * 注: 此接口对KeyPress、KeyPressChar、KeyPressStr起作用
     * 具体表现是键盘按下和弹起的间隔会在当前设定延时的基础上，上下随机浮动50%
     * 假如设定的键盘延时是100，那么这个延时可能就是50-150之间的一个值
     * 设定延时的函数是 SetKeypadDelay
     */
    fun Dispatch.enableRealKeypad(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "EnableRealKeypad", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 鼠标动作模拟真实操作，带移动轨迹，以及点击延时随机
     * @receiver Dispatch
     * @param enable Boolean
     * 0: 关闭模拟
     * 1: 开启模拟(直线模拟)
     * 2: 开启模式(随机曲线，更接近真实)
     * @param mouse_delay Int 单位是毫秒，表示在模拟鼠标移动轨迹时每移动一次的时间间隔(这个值越大，鼠标移动越慢)
     * @param mouse_step Int 表示在模拟鼠标移动轨迹时每移动一次的距离(这个值越大，鼠标移动越快速)
     * @return Boolean 操作是否成功
     */
    fun Dispatch.enableRealMouse(enable: Int, mouse_delay: Int, mouse_step: Int): Boolean {
        return Dispatch.call(this, "EnableRealMouse", enable, mouse_delay, mouse_step).int == 1
    }

    /**
     * 函数简介: 设置是否开启高速dx键鼠模式(默认关闭)
     * @receiver Dispatch
     * @param enable Boolean
     * @return Boolean
     * 注意: 此函数开启的后果就是，所有dx键鼠操作将不会等待，适用于某些特殊的场合(比如避免窗口无响应导致宿主进程也卡死的问题)
     * EnableMouseSync和EnableKeyboardSync开启以后，此函数就无效了
     * 此函数可能在部分窗口下会有副作用，谨慎使用!!
     */
    fun Dispatch.enableSpeedDx(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "EnableSpeedDx", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 强制解除绑定窗口,并释放系统资源
     * @param hwnd Int 需要强制解除绑定的窗口句柄
     * @return Boolean 操作是否成功
     * 注: 此接口一般用在BindWindow和BindWindowEx中，使用了模式1 3 5 7或者属性dx.public.hide.dll后，
     * 在线程或者进程结束后，没有正确调用UnBindWindow而导致下次绑定无法成功时，可以先调用这个函数强制解除绑定，并释放资源，再进行绑定
     * 此接口不可替代UnBindWindow. 只是用在非常时刻，切记！！！
     * 一般情况下可以无条件的在BindWindow或者BindWindowEx之前调用一次此函数，以保证此刻窗口处于非绑定状态
     * 另外，需要注意的是此函数只可以强制解绑在同进程绑定的窗口，不可在不同的进程解绑别的进程绑定的窗口(会产生异常)
     */
    fun Dispatch.forceUnBindWindow(hwnd: Int): Boolean {
        return Dispatch.call(this, "ForceUnBindWindow", hwnd).int == 1
    }

    /**
     * 函数简介: 获取当前对象已经绑定的窗口句柄，无绑定返回0
     * @return Int
     */
    fun Dispatch.getBindWindow(): Int {
        return Dispatch.call(this, "GetBindWindow").int
    }

    /**
     * 函数简介: 获取绑定窗口的fps(即时fps，不是平均fps)
     * 要想获取fps，那么图色模式必须是dx模式的其中一种(比如dx.graphic.3d、dx.graphic.opengl等)
     * @return Int fps值
     */
    fun Dispatch.getFps(): Int {
        return Dispatch.call(this, "GetFps").int
    }

    /**
     * 函数简介: 对目标窗口设置加速功能(类似变速齿轮)，必须在绑定参数中有dx.public.hack.speed时才会生效
     * @receiver Dispatch
     * @param rate Double 取值范围大于0
     * 默认是1.0，表示不加速，也不减速
     * 小于1.0表示减速，大于1.0表示加速
     * 精度为小数点后1位，也就是说1.5和1.56其实是一样的
     * @return Boolean
     * 注意: 此接口必须在绑定窗口成功以后调用，而且必须有参数dx.public.hack.speed。不一定对所有窗口有效，具体自己测试
     */
    fun Dispatch.hackSpeed(rate: Double): Boolean {
        return Dispatch.call(this, "HackSpeed", rate).int == 1
    }

    /**
     * 函数简介: 判定指定窗口是否已经被后台绑定(前台无法判定)
     * @param hwnd Int
     * @return Boolean
     * false: 没绑定或者窗口不存在
     * true: 已经绑定
     */
    fun Dispatch.isBind(hwnd: Int): Boolean {
        return Dispatch.call(this, "IsBind", hwnd).int == 1
    }

    /**
     * 函数简介: 锁定指定窗口的图色数据(不刷新)
     * @param enable Boolean true开启锁定 false关闭锁定
     * @return Boolean 操作是否成功
     * 注意: 此接口只对图色为dx.graphic.3d、dx.graphic.3d.8、dx.graphic.2d、dx.graphic.2d.2有效
     */
    fun Dispatch.lockDisplay(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "LockDisplay", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 禁止外部输入到指定窗口
     * @param lock Int
     * 0: 关闭锁定
     * 1: 开启锁定(键盘鼠标都锁定)
     * 2: 只锁定鼠标
     * 3: 只锁定键盘
     * 4: 同1，但当您发现某些特殊按键无法锁定时，比如(回车，ESC等)，那就用这个模式吧
     * 但此模式会让SendString函数后台失效，或者采用和SendString类似原理发送字符串的其他3方函数失效
     * 5: 同3，但当您发现某些特殊按键无法锁定时，比如(回车，ESC等)，那就用这个模式吧
     * 但此模式会让SendString函数后台失效，或者采用和SendString类似原理发送字符串的其他3方函数失效
     * @return Boolean 操作是否成功
     * 注意: 此接口只针对dx键鼠，普通键鼠无效
     * 有时候，绑定为dx2鼠标模式时(或者没有锁定鼠标位置或状态时)，在脚本处理过程中，在某个时候需要临时锁定外部输入，以免外部干扰，那么这个函数就非常有用
     * 比如某个信息，需要鼠标移动到某个位置才可以获取，但这时，如果外部干扰，那么很可能就会获取失败，所以，这时候就很有必要锁定外部输入
     * 当然，锁定完以后，记得要解除锁定，否则外部永远都无法输入了，除非解除了窗口绑定
     */
    fun Dispatch.lockInput(lock: Int): Boolean {
        return Dispatch.call(this, "LockInput", lock).int == 1
    }

    /**
     * 函数简介: 设置前台鼠标在屏幕上的活动范围
     * @param x1 Int
     * @param y1 Int
     * @param x2 Int
     * @param y2 Int
     * @return Boolean 操作是否成功
     * 注意: 调用此函数后，一旦有窗口切换或者窗口移动的动作，那么限制立刻失效
     * 如果想一直限制鼠标范围在指定的窗口客户区域，那么你需要启动一个线程，并且时刻监视当前活动窗口，然后根据情况调用此函数限制鼠标范围
     */
    fun Dispatch.lockMouseRect(x1:Int, y1:Int,x2:Int,y2:Int): Boolean {
        return Dispatch.call(this, "LockMouseRect", x1,y1,x2,y2).int == 1
    }

    /**
     * 函数简介: 设置开启或者关闭系统的Aero效果(仅对WIN7及以上系统有效)
     * @param enable Boolean
     * @return Boolean
     * 注意: 如果您发现当图色后台为dx2 gdi dx3时，如果有发现目标窗口刷新速度过慢，那可以考虑关闭系统Aero(当然这仅仅是可能的原因)
     */
    fun Dispatch.setAero(enable: Boolean = true): Boolean {
        return Dispatch.call(this, "SetAero", if (enable) 1 else 0).int == 1
    }

    /**
     * 函数简介: 设置dx截图最长等待时间。内部默认是3000毫秒，一般用不到调整这个
     * @param time Int 等待时间，单位是毫秒。注意这里不能设置的过小，否则可能会导致截图失败，从而导致图色函数和文字识别失败
     * @return Boolean 操作是否成功
     * 注: 此接口仅对图色为dx.graphic.3d、dx.graphic.3d.8、dx.graphic.2d、dx.graphic.2d.2有效(其他图色模式无效)
     * 默认情况下，截图需要等待一个延时，超时就认为截图失败，这个接口可以调整这个延时
     * 某些时候或许有用。比如当窗口图色卡死(这时获取图色一定都是超时)，并且要判断窗口卡死，那么这个设置就很有用了
     */
    fun Dispatch.setDisplayDelay(time: Int): Boolean {
        return Dispatch.call(this, "SetDisplayDelay", time).int == 1
    }

    /**
     * 函数简介: 设置opengl图色模式的强制刷新窗口等待时间(内置为400毫秒)
     * @param time Int 等待时间，单位是毫秒(这个值越小，强制刷新就越频繁，相应的窗口可能会导致闪烁)
     * @return Boolean 操作是否成功
     */
    fun Dispatch.setDisplayRefreshDelay(time: Int): Boolean {
        return Dispatch.call(this, "SetDisplayRefreshDelay", time).int == 1
    }

    /**
     * 函数简介: 在不解绑的情况下切换绑定窗口(必须是同进程窗口)
     * @param hwnd Int 需要切换过去的窗口句柄
     * @return Boolean 操作是否成功
     * 注意: 此函数一般用在绑定以后，窗口句柄改变了的情况。如果必须不解绑，那么此函数就很有用了
     */
    fun Dispatch.switchBindWindow(hwnd: Int): Boolean {
        return Dispatch.call(this, "SwitchBindWindow", hwnd).int == 1
    }

    /**
     * 解除绑定窗口,并释放系统资源
     */
    fun Dispatch.unBindWindow(): Boolean {
        return Dispatch.call(this, "UnBindWindow").int == 1
    }

}