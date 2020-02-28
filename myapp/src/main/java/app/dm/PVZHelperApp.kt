package app.dm

import base.constant.*
import base.utils.DmUtils
import base.utils.Win32Utils
import sample.base.BaseApp
import java.util.*

@AppTitle("植物大战僵尸辅助")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("pvz")
open class PVZHelperApp : BaseApp(), DmUtils, Win32Utils {

    override fun init(window: Window) {
        var wh = 0
        //初始化和注册大漠插件
        val dm = initDmCom()
        dm.reg().pln("注册结果：")
        //开启线程检测游戏进程是否运行
        Timer().schedule(object : TimerTask() {
            override fun run() {
                wh = dm.findWindow("MainWindow", "Plants vs. Zombies")
                runOnMainThread {
                    lb("lbCheckRun").text = if (wh != 0) "检测到游戏正在运行" else "未检测到游戏运行"
                }
            }
        }, 0, 1000)

        tf("tfSunshine").textProperty().addListener { observable, oldValue, newValue ->
            if (newValue.matches(Regex("^[0-9]*\$")) && newValue.length <= 4) {
                tf("tfSunshine").text = newValue
            } else {
                tf("tfSunshine").text = oldValue
            }
        }

        //点击设置阳光值
        btn("btnAutoPick").click {
            when {
                wh == 0 -> alert("未检测到游戏进程")
                tf("tfSunshine").text.isEmpty() -> alert("请输入阳光值")
                else -> {
                    //根据基址获取一级偏移地址
                    val first = (dm.readInt(wh, "00755E0C", 0) + "868".toInt(16)).toString(16)
                    //根据一级偏移地址获取二级偏移地址
                    val second = (dm.readInt(wh, first, 0) + "5578".toInt(16)).toString(16)
                    //修改阳光数量
                    dm.writeInt(wh, second, DmUtils.Type.BIT32, tf("tfSunshine").text.toInt())
                }
            }
        }
        //植物无CD
        btn("ncd").click {
            alert(dm.writeData(wh, "0049CDFC", "90 90 90"))
        }
        //自动收集阳光
        btn("pick").click {
            alert(dm.writeData(wh, "0043CC72", "EB 09"))
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PVZHelperApp::class.java)
        }
    }
}