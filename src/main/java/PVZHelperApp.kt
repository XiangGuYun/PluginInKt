import base.*
import base.utils.DmUtils
import base.utils.Key
import base.utils.Win32Utils
import javafx.application.Platform
import sample.base.KotlinActivity

@AppTitle("植物大战僵尸辅助")
@Resizable(false)
@AppIcon("sanguo.png")
@LayoutId("pvz")
class PVZHelperApp : KotlinActivity(), DmUtils, Win32Utils {

    var wh = 0

    override fun init(window: Window) {
        val dm = initDmCom()
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
            if(wh == 0){
                alert("未检测到游戏进程")
            } else {
                alert(dm.writeInt(wh, "2EA036F8", DmUtils.Type.BIT32, 150))
            }
        }

        regHK(1, Key.f1)

        regHK(2, Key.f, CTRL+SHIFT)
                                2
        setHKListener {
            when(it){
                1-> {
                   getProcessList().forEach {
                       println(it.first+" "+it.second)
                   }
                }
                2->{
                    alert("这不是爱")
                }
            }
        }

    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PVZHelperApp::class.java)
        }
    }
}