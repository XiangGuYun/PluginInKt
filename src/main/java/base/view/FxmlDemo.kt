package base.view

import base.constant.AppTitle
import base.constant.LayoutId
import base.constant.Window
import base.control.DemoCtrl
import javafx.fxml.FXMLLoader
import sample.base.BaseApp

/**
 * 问题1：id与fx:id的区别？
 * 答：fx:id的作用是在Control中调用，但是Control不好用，因此用id即可。
 *
 * 问题2：stylesheets、styleClass、style之间的区别？
 * 答：stylesheets是Parent（包括子类）中的css文件的集合，可以为其添加多个css文件，css文件中定义的样式可以供它自身以及所有子控件调用。
 * styleClass指的是css文件中的某个样式，该样式名称为“.样式名”，需要在代码或XML中指定才能调用。
 * 也可为专门一个控件创建样式，样式名称需要为“#样式名”，无需指定便可调用。
 * style指的是单独一条样式属性。
 * 备注：在BaseApp已经为主窗口的Scene默认添加了style.css文件
 *
 * 样式属性举例
 *  -fx-text-fill: rgba(17, 145,  213);
 *  -fx-border-color:  rgba(255, 255,  255,  .80);
 *  -fx-border-radius: 8;
 *  -fx-padding: 6  6  6  6;
 *  -fx-background-color: white, rgb(189,218,230), white;
 *  -fx-background-radius: 50%;
 *  -fx-background-insets: 0, 1, 2;
 *  -fx-font-family:  "Helvetica";
 *  -fx-text-fill:  black;
 *  -fx-font-size:  20px;
 */
@AppTitle("FxmlDemo")
@LayoutId("demo1")
class FxmlDemo : BaseApp(){

    override fun init(window: Window) {
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(FxmlDemo::class.java)
        }
    }

}