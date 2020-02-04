package base.utils

import com.sun.jna.platform.win32.*
import com.sun.jna.platform.win32.WinNT.HANDLE
import com.sun.jna.platform.win32.WinDef.DWORD
import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32


interface Win32Utils {

    val user32 get() =  User32.INSTANCE
    val shell32 get() = Shell32.INSTANCE
    val kernel32 get() = Kernel32.INSTANCE
    /**
     * 获取窗口句柄
     */
    fun getWindowHandle(className:String?, title:String?): WinDef.HWND {
        return User32.INSTANCE.FindWindow(className, title)
    }

    /**
     * 获取窗口矩形
     */
    fun getWindowRect(className:String?, title:String?): WinDef.RECT {
        val wh = getWindowHandle(className, title)
        val rect = WinDef.RECT()
        User32.INSTANCE.GetWindowRect(wh, rect)
        return rect
    }

    /**
     * 获取窗口宽高
     */
    fun getWindowSize(className:String?, title:String?):Pair<Int, Int>{
        val wh = getWindowHandle(className, title)
        val rect = WinDef.RECT()
        User32.INSTANCE.GetWindowRect(wh, rect)
        return rect.right-rect.left to rect.bottom-rect.top
    }

    /**
     * 运行应用程序
     */
    fun runApp(appPath:String): Process? {
        val runtime  = Runtime.getRuntime()
        val process = runtime.exec(appPath)
        return process
    }

    /**
     * 获取所有进程的id和名称
     */
    fun getProcessList(): ArrayList<Pair<String, String>> {
        val lib = Kernel32.INSTANCE
        // 获取进程快照
        val hSnapshot = lib.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, DWORD(0))
        // 获取进程
        val pe32 = PROCESSENTRY32()
        val processList = ArrayList<Pair<String, String>>()
        while (lib.Process32Next(hSnapshot, pe32)) {
            val processId = pe32.th32ProcessID
            val name = pe32.szExeFile
            processList.add(processId.toString() to String(name))
            // 打开进程
            //val processHandle = lib.OpenProcess(WinNT.PROCESS_ALL_ACCESS, false, pe32.th32ProcessID.toInt())
        }
        return processList
    }

    /**
     * 清空回收站
     */
    fun clearRecycleBin(){

    }


}