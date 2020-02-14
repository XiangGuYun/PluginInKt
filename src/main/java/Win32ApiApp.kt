import base.constant.*
import base.utils.DmUtils
import base.utils.Win32Utils
import com.sun.jna.platform.win32.*
import sample.base.BaseApp

@AppTitle("Win32Api学习")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("win32api")
class Win32ApiApp : BaseApp(), DmUtils, Win32Utils {

    override fun init(window: Window) {

        //清空回收站
        btn("btnClearRecycleBin").click{
            shell32.SHEmptyRecycleBin(user32.GetActiveWindow(), null, 0)
        }

        //取窗口标题
        btn("btnGetWindowText").click {
            val ca = CharArray(255)
            user32.GetWindowText(user32.GetActiveWindow(), ca, 255)
            alert("结果：${String(ca)}")
        }

        //取窗口类名
        btn("btnGetClassName").click{
            val ca = CharArray(255)
            user32.GetClassName(user32.GetActiveWindow(), ca, 255)
            alert("窗口类名是${String(ca)}")
        }

        //取窗口矩形
        btn("btnGetWindowRect").click {
            val rect = WinDef.RECT()
            user32.GetWindowRect(user32.GetActiveWindow(), rect)
            alert("窗口的矩形数据是[${rect.left},${rect.top},${rect.right},${rect.bottom}]")
        }

        //枚举窗口
        btn("btnEnumWindows").click {
            alert(user32.EnumWindows({ hwnd, pointer ->
                val ca = CharArray(255)
                user32.GetWindowText(hwnd, ca, 255)
                println(String(ca))
                true
            }, user32.GetActiveWindow().pointer))
        }

        val dm = initDmCom()

        //取进程ID
        btn("btnGetPID").click {
//            val wh = user32.FindWindow("notepad", null)
//            println(wh)
            alert(kernel32.GetCurrentProcessId())
//            alert(dm.getWindowProcessId(dm.findWindow("notepad", null)))
        }

        //关闭指定窗口
        btn("btnCloseWindow").click {
            //最小化
            //user32.CloseWindow(user32.FindWindow("notepad", null))
            //销毁
            user32.SendMessage(user32.FindWindow("notepad", null), WinUser.WM_CLOSE, null, null)
        }

        //修改窗口大小
        btn("btnSetWindowSize").click {
            user32.SetWindowPos(user32.FindWindow("notepad", null), null, 100, 100, 300, 300, WinUser.SWP_DRAWFRAME)
        }

        //创建文件
        btn("btnCreateFile").click {
            kernel32.CreateFile(
                    "${DESKTOP}newFile.txt",
                    WinNT.GENERIC_WRITE,
                    WinNT.FILE_SHARE_WRITE,
                    null,
                    WinNT.CREATE_ALWAYS,
                    WinNT.FILE_ATTRIBUTE_NORMAL,
                    null
            )
        }

        //删除文件
        btn("btnDeleteFile").click {
            kernel32.DeleteFile("${DESKTOP}test.txt")
        }


        //写入文本
        btn("btnSendString").click {
            dm.setClipboard("xxxxxxxxxxx")
            user32.SendMessage(user32.FindWindow("notepad", null), WinUser.IDC_HAND, null, null)
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Win32ApiApp::class.java)
        }
    }
}