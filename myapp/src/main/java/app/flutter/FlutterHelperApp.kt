package app.flutter

import app.applet.CopyUtils
import base.constant.*
import javafx.stage.StageStyle
import sample.base.BaseApp
import java.io.File
import java.lang.StringBuilder

@AppIcon("flutter.png")
@AppTitle("Flutter资源ID生成")
@LayoutId("flutter_helper")
class FlutterHelperApp : BaseApp(){

    override fun init(window: Window) {

        tf("tfPreName").text = "assets/images"

        btn("btnGenId").clickBN {
            val path = tf("tfPath").text
            if(path.isEmpty()){
                alert("请输入文件夹路径！")
                return@clickBN
            }
            val preName = tf("tfPreName").text
            if(preName.isEmpty()){
                alert("请输入前缀名！")
                return@clickBN
            }
            val file = File(path)
            if(!file.exists()){
                alert("文件夹路径不存在！")
                return@clickBN
            }
            val builder = StringBuilder()
            println("==============="+file.listFiles().size)
            file.listFiles().forEach {
                if(it.isFile){
                    builder.append("    - "+preName+"/"+it.name+"\n")
                    builder.append("    - "+preName+"/2.0x/"+it.name+"\n")
                    builder.append("    - "+preName+"/3.0x/"+it.name+"\n")
                }
            }
            println("==============="+builder.toString())
            CopyUtils.copyString(builder.toString())
            alert("已复制到剪贴板")
        }

        btn("btnGenConstant").clickBN {
            val path = tf("tfPath").text
            if(path.isEmpty()){
                alert("请输入文件夹路径！")
                return@clickBN
            }
            val preName = tf("tfPreName").text
            if(preName.isEmpty()){
                alert("请输入前缀名！")
                return@clickBN
            }
            val file = File(path)
            if(!file.exists()){
                alert("文件夹路径不存在！")
                return@clickBN
            }
            val builder = StringBuilder()
            println("==============="+file.listFiles().size)
            file.listFiles().forEach {
                if(it.isFile){
                    builder.append("static const String ${it.nameWithoutExtension.toUpperCase()} = "+"'"+preName+"/"+it.name+"'"+"\n")
                }
            }
            println("==============="+builder.toString())
            CopyUtils.copyString(builder.toString())
            alert("已复制到剪贴板")
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(FlutterHelperApp::class.java)
        }
    }

}