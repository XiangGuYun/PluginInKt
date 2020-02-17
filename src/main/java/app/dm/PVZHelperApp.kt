package app.dm

import base.constant.*
import base.utils.DmUtils
import base.utils.Win32Utils
import javafx.application.Platform
import sample.base.BaseApp

@AppTitle("植物大战僵尸辅助")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("pvz")
class PVZHelperApp : BaseApp(), DmUtils, Win32Utils {

    var wh = 0
    var sunshineBaseAddress = 0x00755E0C
    var sunshineFirstOffset = 0x868
    var sunshineSecondOffset = 0x5578

    override fun init(window: Window) {
        val dm = initDmCom()
        dm.reg()
        Thread{
          while (true){
              wh = dm.findWindow("MainWindow", "Plants vs. Zombies")
              if(wh != 0){
                  Platform.runLater {
                      lb("lbCheckRun").text = "检测到游戏正在运行"
                  }
              } else {
                  Platform.runLater {
                      lb("lbCheckRun").text = "未检测到游戏运行"
                  }
              }
              Thread.sleep(500)
          }
        }.start()

        btn("btnAutoPick").click {
            when{
                wh==0 -> {
                    alert("未检测到游戏进程")
                }
                tf("tfSunshine").text.isEmpty() ->{
                    alert("请输入阳光值")
                }
                else -> {
    //                val first = dm.readInt(wh, "[00755E0C]+868", 0).toString(16)
    //                println(first)
    //                val second = dm.readInt(wh, "[${first}]+5578", 0).toString(16)
    //                println(second)
                    val first = (dm.readInt(wh, "00755E0C", 0)+"868".toInt(16)).toString(16)
                    println(first)
                    val second = (dm.readInt(wh, first, 0)+"5578".toInt(16)).toString(16)
                    println(second)
                    dm.writeInt(wh, second, DmUtils.Type.BIT32, tf("tfSunshine").text.toInt())
                }
            }
        }

        btn("ncd").click{
            alert(dm.writeData(wh, "0049CDFC", "90 90 90"))
        }

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