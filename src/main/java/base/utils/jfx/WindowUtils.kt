package base.utils.jfx

import javafx.scene.control.TreeView
import javafx.stage.Modality
import javafx.stage.Stage

/**
 * JavaFX中的Window、Scene、Node类似于Android中的Activity、Fragment、ViewGroup，稍有不同的Scene是必需的中间层
 *
Window常用方法
show() //显示
hide() //隐藏
isIconified = false //最下化
isMaximized = false //最大化
close()
width = 500.0
height = 500.0
maxWidth = 1000.0
maxHeight = 1000.0
minWidth = 100.0
minHeight = 100.0
isFullScreen = true
opacity = 0.5 //透明度
isAlwaysOnTop = true //始终显示在最前面
x = 100 //左上角坐标x
y = 100 //左上角坐标y
initStyle(StageStyle.DECORATED) // 默认，带有装饰（icon，标题，最小化，最大化，关闭）
initStyle(StageStyle.UNDECORATED) // 没有装饰
initStyle(StageStyle.TRANSPARENT) // 装饰变透明
initStyle(StageStyle.UNIFIED) // 统一，装饰栏与窗口内部的颜色均为白色
initStyle(StageStyle.UTILITY) // 通用，只有关闭按钮
initModality(Modality.APPLICATION_MODAL) // （对话框模式）强制只能在当前窗口操作，只有关闭此窗口才能操作其他窗口；
效果同initOwner(mainWindow)+initModality(Modality.WINDOW_MODAL)

 Scene常用方法
 cursor = Cursor.CLOSED_HAND //设置鼠标指针形状，也可自定义javaClass.classLoader.getResource("图片").toExternalForm()
 */
interface WindowUtils {


    fun Stage.size(width:Number, height:Number){
        this.width = width.toDouble()
        this.height = height.toDouble()
    }

    /**
     * 设置窗口为不可取消的对话框形态
     * 备注：此方法不能用于主窗口
     */
    fun Stage.setUnCancelableDialog(){
        initModality(Modality.APPLICATION_MODAL)
    }

    /**
     * 监听窗口左上角X坐标值的改变
     */
    fun Stage.onLocXChanged(callback: (x:Int) -> Unit){
        xProperty().addListener { observable, oldValue, newValue ->
            callback.invoke(newValue!!.toInt())
        }
    }

    /**
     * 监听窗口左上角Y坐标值的改变
     */
    fun Stage.onLocYChanged(callback: (y:Int) -> Unit){
        yProperty().addListener { observable, oldValue, newValue ->
            callback.invoke(newValue!!.toInt())
        }
    }

    /**
     * 监听窗口宽度变化时的最新值
     */
    fun Stage.onWidthChanged(callback: (Int) -> Unit) {
        widthProperty().addListener { observable, oldValue, newValue ->
            callback.invoke(newValue!!.toInt())
        }
    }

    /**
     * 监听窗口高度变化时的最新值
     */
    fun Stage.onHeightChanged(callback: (Int) -> Unit) {
        heightProperty().addListener { observable, oldValue, newValue ->
            callback.invoke(newValue!!.toInt())
        }
    }

}