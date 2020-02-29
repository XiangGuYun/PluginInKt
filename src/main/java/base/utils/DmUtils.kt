package base.utils

import com.jacob.activeX.ActiveXComponent
import com.jacob.com.Dispatch
import java.awt.MouseInfo
import java.awt.Point


interface DmUtils {

    /**
     * 初始化大漠插件
     */
    fun initDmCom(): Dispatch {
        return ActiveXComponent("dm.dmsoft").`object`
    }

    fun Dispatch.reg(): Int {
        return Dispatch.call(this, "Reg", "yida94563887ce815854f20ab8c0926f99a987b4cea", "uf3am").int
    }

    /**
     * 获取当前大漠的版本
     */
    fun getDmVersion(): String {
        return ActiveXComponent("dm.dmsoft").invoke("Ver").string
    }

    /*******************************************************************************************************************
     * 窗口模块
     *******************************************************************************************************************/

    /**
     * 获取窗口句柄
     */
    fun Dispatch.findWindow(windowClass: String?, windowTitle: String?): Int {
        return Dispatch.call(this, "FindWindow", windowClass, windowTitle).int
    }

    fun Dispatch.findWindowEx(parent: Int, windowClass: String?, windowTitle: String?): Int {
        return Dispatch.call(this, "FindWindowEx", parent, windowClass, windowTitle).int
    }

    fun Dispatch.enumWindow(parent: Int = 0, title: String?, class_name: String?, filter: Int = 1): String {
        return Dispatch.call(this, "EnumWindow", parent, title, class_name, filter).string
    }

    /**
     * 设置窗口宽高
     */
    fun Dispatch.setWindowSize(windHandle: Long, width: Int, height: Int): Boolean {
        val result = Dispatch.call(this, "SetWindowSize", windHandle, width, height).int
        return result == 1
    }

    /**
     * 获取窗口标题
     */
    fun Dispatch.getWindowTitle(windHandle: Long): String {
        return Dispatch.call(this, "GetWindowTitle", windHandle).toString()
    }

    fun Dispatch.getWindowRect(wh: Int, x1: Int, y1: Int,
                               x2: Int, y2: Int): Boolean {
        return Dispatch.call(this, "GetWindowRect", wh,
                x1, y1, x2, y2).int == 1
    }

    /**
     * 获取窗口类名
     */
    fun Dispatch.getWindowClass(wh: Int): String {
        return Dispatch.call(this, "GetWindowClass", wh).toString()
    }

    /**
     * 获取窗口进程ID
     */
    fun Dispatch.getWindowProcessId(wh: Int): Int {
        return Dispatch.call(this, "GetWindowProcessId", wh).int
    }

    /**
     * 向指定窗口发送文本数据
     */
    fun Dispatch.sendString(wh: Int, str: String): Boolean {
        return Dispatch.call(this, "SendString", wh, str).int == 1
    }

    fun Dispatch.sendString2(wh: Int, str: String): Boolean {
        return Dispatch.call(this, "SendString2", wh, str).int == 1
    }

    /**
     * 设置窗口标题
     */
    fun Dispatch.setWindowText(wh: Int, title: String): Boolean {
        return Dispatch.call(this, "SetWindowText", wh, title).int == 1
    }


    /*******************************************************************************************************************
     * 键鼠模块
     *******************************************************************************************************************/

    /**
     * 获取鼠标指针位置
     */
    fun getCursorPos(): Point {
        val pinfo = MouseInfo.getPointerInfo()
        val p = pinfo.location
        val x = p.getX()
        val y = p.getY()
        return Point(x.toInt(), y.toInt())
    }

    /**
     * 按住某键（根据按键码）
     */
    infix fun Dispatch.keyDown(vk_code: Int): Boolean {
        return Dispatch.call(this, "KeyDown", vk_code).int == 1
    }

    /**
     * 按住某键（根据键位上的字符，例如enter、1、F1、a、B，不区分大小写）
     */
    fun Dispatch.keyDownChar(vk_str: String): Boolean {
        return Dispatch.call(this, "KeyDownChar", vk_str).int == 1
    }

    /**
     * 按下某键（根据按键码）
     */
    infix fun Dispatch.keyPress(vk_code: Int): Boolean {
        return Dispatch.call(this, "KeyPress", vk_code).int == 1
    }

    /**
     * 按下某键（根据键位上的字符，例如enter、1、F1、a、B，不区分大小写）
     */
    fun Dispatch.keyPressChar(vk_str: String): Boolean {
        return Dispatch.call(this, "KeyPressChar", vk_str).int == 1
    }

    /**
     * 根据指定的字符串序列，依次按顺序按下其中的字符
     * @param delay 按下字符后的间隔时间，单位是毫秒
     */
    fun Dispatch.keyPressStr(vk_str: String, delay: Int): Boolean {
        return Dispatch.call(this, "KeyPressStr", vk_str, delay).int == 1
    }

    /**
     * 松开某键（根据按键码）
     */
    infix fun Dispatch.keyUp(vk_code: Int): Boolean {
        return Dispatch.call(this, "KeyUp", vk_code).int == 1
    }

    /**
     * 松开某键（根据键位上的字符，例如enter、1、F1、a、B，不区分大小写）
     */
    fun Dispatch.keyUpChar(vk_str: String): Boolean {
        return Dispatch.call(this, "KeyUpChar", vk_str).int == 1
    }

    /**
     * 单击鼠标左键
     */
    fun Dispatch.leftClick(): Boolean {
        return Dispatch.call(this, "LeftClick").int == 1
    }

    /**
     * 双击鼠标左键
     */
    fun Dispatch.leftDoubleClick(): Boolean {
        return Dispatch.call(this, "LeftDoubleClick").int == 1
    }

    /**
     * 按住鼠标左键
     */
    fun Dispatch.leftDown(): Boolean {
        return Dispatch.call(this, "LeftDown").int == 1
    }

    /**
     * 松开鼠标左键
     */
    fun Dispatch.leftUp(): Boolean {
        return Dispatch.call(this, "LeftUp").int == 1
    }

    /**
     * 按下鼠标滚轮
     */
    fun Dispatch.middleClick(): Boolean {
        return Dispatch.call(this, "MiddleClick").int == 1
    }

    /**
     * 移动鼠标到某坐标点
     */
    fun Dispatch.moveTo(x: Int, y: Int): Boolean {
        return Dispatch.call(this, "MoveTo", x, y).int == 1
    }

    /**
     * 偏移鼠标坐标点
     */
    fun Dispatch.moveR(x: Int, y: Int) {
        Dispatch.call(this, "MoveR", x, y)
    }

    /**
     * 移动鼠标到指定区域的随机一个点上
     */
    fun Dispatch.moveToEx(x: Int, y: Int, w: Int, h: Int): String {
        return Dispatch.call(this, "MoveToEx", x, y, w, h).string
    }

    /**
     * 按下鼠标右键
     */
    fun Dispatch.rightClick(): Boolean {
        return Dispatch.call(this, "RightClick").int == 1
    }

    /**
     * 按住鼠标右键
     */
    fun Dispatch.rightDown(): Boolean {
        return Dispatch.call(this, "RightDown").int == 1
    }

    /**
     * 松开鼠标右键
     */
    fun Dispatch.rightUp(): Boolean {
        return Dispatch.call(this, "RightUp").int == 1
    }

    /**
     * 键鼠类型
     * NORMAL：对应normal键盘，默认内部延时为30ms；对应normal鼠标，默认内部延时为30ms
     * WINDOWS：对应windows键盘，默认内部延时为10ms；对应windows鼠标，默认内部延时为10ms
     * DX：对应dx键盘，默认内部延时为50ms；对应dx鼠标，默认内部延时为40ms
     */
    enum class KMType {
        NORMAL, WINDOWS, DX
    }

    /**
     * 设置按键时，键盘按下和弹起的时间间隔
     */
    fun Dispatch.setKeypadDelay(type: KMType, delay: Int): Boolean {
        return Dispatch.call(this, "SetKeypadDelay",
                when (type) {
                    KMType.NORMAL -> "normal"
                    KMType.WINDOWS -> "windows"
                    else -> "dx"
                },
                delay).int == 1
    }

    /**
     * 设置鼠标时，鼠标按下和弹起的时间间隔
     */
    fun Dispatch.setMouseDelay(type: KMType, delay: Int): Boolean {
        return Dispatch.call(this, "SetMouseDelay",
                when (type) {
                    KMType.NORMAL -> "normal"
                    KMType.WINDOWS -> "windows"
                    else -> "dx"
                },
                delay).int == 1
    }

    /**
     * 等待指定的按键按下 (前台,不是后台)
     * @param time_out 等待多久，单位毫秒。如果是0，表示一直等待
     */
    fun Dispatch.waitKey(vk_code: Int, time_out: Int) {
        Dispatch.call(this, "WaitKey", vk_code, time_out)
    }

    /**
     * 滚轮向下滚
     */
    fun Dispatch.wheelDown(): Boolean {
        return Dispatch.call(this, "WheelDown").int == 1
    }

    /**
     * 滚轮向上滚
     */
    fun Dispatch.wheelUp(): Boolean {
        return Dispatch.call(this, "WheelUp").int == 1
    }

    /*******************************************************************************************************************
     * 图色模块
     *******************************************************************************************************************/

    /**
     * 截图
     */
    fun Dispatch.capture(x1: Int, y1: Int, x2: Int, y2: Int, filePath: String): Boolean {
        return Dispatch.call(this, "Capture", x1, y1, x2, y2, filePath).int == 1
    }


    fun Dispatch.getColor(x: Int, y: Int): String {
        return Dispatch.call(this, "GetColor", x, y).string
    }

    /*
    0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     */
    enum class DIR {
        LR_TB, LR_BT, RL_TB, RL_BT
    }

    /**
     * 查找指定区域内的图片
     * 这个函数可以查找多个图片,只返回第一个找到的X Y坐标
     * @param x1 整形数:区域的左上X坐标
     * @param y1 整形数:区域的左上Y坐标
     * @param x2 整形数:区域的右下X坐标
     * @param y2 整形数:区域的右下Y坐标
     * @param picName 字符串:图片名,可以是多个图片,比如"test.bmp|test2.bmp|test3.bmp"
     * @param deltaColor 字符串:颜色色偏比如"203040" 表示RGB的色偏分别是20 30 40 (这里是16进制表示)
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @param dir 整形数:查找方向 0: 从左到右,从上到下 1: 从左到右,从下到上 2: 从右到左,从上到下 3: 从右到左, 从下到上
     */
    fun Dispatch.findPic(x1: Int, y1: Int, x2: Int, y2: Int, name: String, deltaColor: String="101010", sim: Double=0.9, dir: DIR = DIR.LR_TB): String {
        val result = Dispatch.call(this, "FindPicE", x1, y1, x2, y2, "$name.bmp", deltaColor, sim,
                when (dir) {
                    DIR.LR_TB -> 0
                    DIR.LR_BT -> 1
                    DIR.RL_TB -> 2
                    else -> 3
                }).string
        println("==========FIND_PIC:图片\"${name}\"查找结果是$result")
        return result
    }

    /*******************************************************************************************************************
     * 系统模块
     *******************************************************************************************************************/

    fun Dispatch.runApp(appPath: String, mode: Int): Boolean {
        return Dispatch.call(this, "RunApp", appPath, mode).int == 1
    }

    fun Dispatch.setClipboard(value: String): Boolean {
        return Dispatch.call(this, "SetClipboard", value).int == 1
    }

    fun Dispatch.getClipboard(): String {
        return Dispatch.call(this, "GetClipboard").string
    }


    /*******************************************************************************************************************
     * 后台模块
     *******************************************************************************************************************/

    enum class Display {
        NORMAL,
        GDI,
        GDI2,
        DX2,
        DX3,
        DX
    }

    enum class Mouse {
        NORMAL,
        WINDOWS,
        WINDOWS2,
        WINDOWS3,
        DX,
        DX2
    }

    enum class Keyboard {
        NORMAL,
        WINDOWS,
        DX
    }

    /**
     * 绑定窗口
     */
    fun Dispatch.bindWindow(hwnd: Int, display: Display, mouse: Mouse, keyboard: Keyboard): Boolean {
        return Dispatch.call(this, "BindWindowEx", hwnd,
                when (display) {
                    Display.NORMAL -> "normal"
                    Display.GDI -> "gdi"
                    Display.GDI2 -> "gdi2"
                    Display.DX2 -> "dx2"
                    Display.DX3 -> "dx3"
                    else -> "dx.graphic.3d"
                },
                when (mouse) {
                    Mouse.NORMAL -> "normal"
                    Mouse.WINDOWS -> "windows"
                    Mouse.WINDOWS2 -> "windows2"
                    Mouse.WINDOWS3 -> "windows3"
                    Mouse.DX -> "dx"
                    else -> "dx2"
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

    fun Dispatch.enableFakeActive(enable: Boolean): Boolean {
        return Dispatch.call(this, "EnableFakeActive", if (enable) 1 else 0).int == 1
    }

    /**
     * 解除绑定窗口,并释放系统资源
     */
    fun Dispatch.unBindWindow(): Boolean {
        return Dispatch.call(this, "UnBindWindow").int == 1
    }


    /*******************************************************************************************************************
     * 基本设置模块
     *******************************************************************************************************************/

    /**
     * 设置全局路径,设置了此路径后,所有接口调用中,相关的文件都相对于此路径. 比如图片,字库等
     */
    fun Dispatch.setPath(path: String): Boolean {
        return Dispatch.call(this, "SetPath", path).int == 1
    }

    /*******************************************************************************************************************
     * 内存模块
     *******************************************************************************************************************/

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

    /*******************************************************************************************************************
     * 文字识别模块
     *******************************************************************************************************************/

    /**
     * 设置字库文件
     * @param index 整形数:字库的序号,取值为0-9,目前最多支持10个字库
     * @param file 字符串:字库文件名
     */
    fun Dispatch.setDict(index: Int, file: String): Boolean {
        return Dispatch.call(this, "SetDict", index, file).int == 1
    }

    /**
     * 识别屏幕范围(x1,y1,x2,y2)内符合color_format的字符串,并且相似度为sim,sim取值范围(0.1-1.0),
     * 这个值越大越精确,越大速度越快,越小速度越慢,请斟酌使用!
     * @param x1 整形数:区域的左上X坐标
     * @param y1 整形数:区域的左上Y坐标
     * @param x2 整形数:区域的右下X坐标
     * @param y2 整形数:区域的右下Y坐标
     * @param color_format 字符串:颜色格式串. 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.
     * 注意，RGB和HSV格式都支持.
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @return 返回识别到的字符串
     */
    fun Dispatch.ocr(x1: Int, y1: Int, x2: Int, y2: Int, color_format: String, sim: Double): String {
        return Dispatch.call(this, "Ocr", x1, y1, x2, y2, color_format, sim).string
    }

//    string FindStrFastE(x1,y1,x2,y2,string,color_format,sim)
    /**
     * 快速在指定区域内查找文字
     *
     * @param x1 整形数:区域的左上X坐标
     * @param y1 整形数:区域的左上Y坐标
     * @param x2 整形数:区域的右下X坐标
     * @param y2 整形数:区域的右下Y坐标
     * @param string 字符串:待查找的字符串, 可以是字符串组合，比如"长安|洛阳|大雁塔",中间用"|"来分割字符串
     * @param color_format 字符串:颜色格式串, 可以包含换行分隔符,语法是","后加分割字符串. 具体可以查看下面的示例.注意，RGB和HSV格式都支持.
     * @param sim 双精度浮点数:相似度,取值范围0.1-1.0
     * @return 返回字符串序号以及X和Y坐标,形式如"id|x|y", 比如"0|100|200",没找到时，id和X以及Y均为-1，"-1|-1|-1"
     *
     * 注: 此函数比FindStrE要快很多，尤其是在字库很大时，或者模糊识别时，效果非常明显。推荐使用此函数。
     * 另外由于此函数是只识别待查找的字符，所以可能会有如下情况出现问题。
     *
     * 比如 字库中有"张和三" 一共3个字符数据，然后待识别区域里是"张和三",如果用FindStrE查找
     * "张三"肯定是找不到的，但是用FindStrFastE却可以找到，因为"和"这个字符没有列入查找计划中
     * 所以，在使用此函数时，也要特别注意这一点。
     */
    fun Dispatch.findStrFast(x1: Int, y1: Int, x2: Int, y2: Int, string: String, color_format: String, sim: Double): String {
        val result = Dispatch.call(this, "FindStrFastE", x1, y1, x2, y2, string, color_format, sim).string
        println("##########文字\"${string}\"的查找结果是$result")
        return result
    }

    /**
     * 绑定雷电模拟器
     */
    fun Dispatch.bindLDMonitor(title: String = "雷电模拟器"): Boolean {
        val wh1 = this.findWindow("LDPlayerMainFrame", title)
        val wh2 = this.findWindowEx(wh1, "RenderWindow", "TheRender")
        return this.bindWindow(wh2, DmUtils.Display.NORMAL, DmUtils.Mouse.WINDOWS, DmUtils.Keyboard.WINDOWS)
    }

    /**
     * 检查是否能找到图片，是则点击此处并返回true
     */
    fun Dispatch.checkAndClick(result: String): Boolean {
        if (check(result)) {
            this.click(result)
            return true
        }
        return false
    }

    fun Dispatch.checkAndDoubleClick(result: String): Boolean {
        if (check(result)) {
            this.doubleClick(result)
            return true
        }
        return false
    }

    /**
     * 是否成功检测到图或字
     */
    fun check(result: String): Boolean {
        return result != "-1|-1|-1"
    }

    /**
     * 将扫描到的位置信息转换为坐标点
     */
    fun String.toPoint(): Point {
        val arr = this.split("|")
        return Point(arr[1].toInt(), arr[2].toInt())
    }

    /**
     * 移动鼠标到指定区域并点击
     */
    fun Dispatch.click(result: String) {
        val arr = result.split("|")
        Thread.sleep(100)
        this.moveTo(arr[1].toInt(), arr[2].toInt())
        Thread.sleep(100)
        this.leftClick()
    }

    fun Dispatch.click(x: Int, y: Int) {
        this.moveTo(x, y)
        Thread.sleep(100)
        this.leftClick()
    }

    fun Dispatch.doubleClick(result: String) {
        val arr = result.split("|")
        Thread.sleep(100)
        this.moveTo(arr[1].toInt(), arr[2].toInt())
        Thread.sleep(100)
        this.leftClick()
        Thread.sleep(100)
        this.leftClick()
    }

    fun Dispatch.doubleClick(x: Int, y: Int) {
        Thread.sleep(100)
        this.moveTo(x, y)
        Thread.sleep(100)
        this.leftClick()
        Thread.sleep(100)
        this.leftClick()
    }

}