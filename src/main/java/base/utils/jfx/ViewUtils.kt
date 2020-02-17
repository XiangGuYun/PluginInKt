package sample.base

import com.sun.javafx.css.Style
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.ButtonBase
import javafx.scene.control.Labeled
import javafx.scene.image.ImageView
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.stage.Screen

interface ViewUtils {

    /**
     * 获取屏幕的宽高信息
     * @isVisual 是否扣除状态栏的高度
     */
    fun getScreenSize(isVisual:Boolean = false): Pair<Int, Int> {
        return when {
            !isVisual -> {
                Screen.getPrimary().bounds.width.toInt() to Screen.getPrimary().bounds.height.toInt()
            }
            else -> {
                Screen.getPrimary().visualBounds.width.toInt() to Screen.getPrimary().visualBounds.height.toInt()
            }
        }
    }

    fun getScreenDpi(): Int {
        return Screen.getPrimary().dpi.toInt()
    }

    fun <T : Node> T.xy(x: Int, y: Int): T {
        layoutX = x.toDouble()
        layoutY = y.toDouble()
        return this
    }

    fun <T : ImageView> T.fitSize(w: Number, h: Number): T {
        if (w != -1)
            fitWidth = w.toDouble()
        if (h != -1)
            fitHeight = h.toDouble()
        return this
    }

    fun <T : Region> T.preSize(w: Number, h: Number): T {
        if (w != -1)
            prefWidth = w.toDouble()
        if (h != -1)
            prefHeight = h.toDouble()
        return this
    }

    fun <T : Region> T.bgColor(bgColor: String): T {
        style = "-fx-background-color:$bgColor"
        return this
    }

    fun <T : Region> T.bgShape(bgColor: String, cornerRadius: Int, insets: Int): T {
        background = Background(BackgroundFill(Paint.valueOf(bgColor), CornerRadii(cornerRadius.toDouble()), Insets(insets.toDouble())))
        return this
    }

    fun <T : Region> T.bgImage(bgColor: String, cornerRadius: Int, padding: Int): T {
//        background = Background(BackgroundImage())
        return this
    }

    fun <T : Region> T.border(borderColor: String, borderStyle: BorderStrokeStyle, cornerRadii: Int, borderWidth: Int): T {
        border = Border(BorderStroke(Paint.valueOf(borderColor), borderStyle, CornerRadii(cornerRadii.toDouble()), BorderWidths(borderWidth.toDouble())))
        return this
    }

    fun <T : Labeled> T.textSize(size: Number): T {
        font = Font.font(size.toDouble())
        return this
    }

    fun <T : Labeled> T.align(pos: Pos): T {
        alignment = pos
        return this
    }

    fun <T : Labeled> T.setTextFamily(family: String): T {
        font = Font.font(family)
        return this
    }

    fun <T : Labeled> T.setTextColor(color: String): T {
        textFill = Color.web(color)
        return this
    }

    /**
     * 鼠标键位点击事件
     * 判断按下的是哪个键 MouseEvent.button.name MIDDLE-中键 PRIMARY-左键 SECONDARY-右键
     * 判断连续点击的次数 MouseEvent.clickCount
     */
    fun <T : Node> T.click(function: (MouseEvent) -> Unit): T {
        setOnMouseClicked {
            //            val view = it.source as T
            function.invoke(it)
        }
        return this
    }

    /**
     * 键盘点击事件
     * 如何获取键位名称？ it.code.getName()
     */
    fun <T : Region> T.pressKey(function: (KeyEvent) -> Unit): T {
        setOnKeyPressed {
            function.invoke(it)
        }
        return this
    }

    fun <T : Region> T.releaseKey(function: (KeyEvent) -> Unit): T {
        setOnKeyReleased {
            function.invoke(it)
        }
        return this
    }

    fun <T : Region> T.typeKey(function: (KeyEvent) -> Unit): T {
        setOnKeyTyped {
            function.invoke(it)
        }
        return this
    }

    /**
     * 鼠标键位按住事件
     */
    fun <T : Region> T.press(function: (MouseEvent) -> Unit): T {
        setOnMousePressed {
            function.invoke(it)
        }
        return this
    }

    /**
     * 鼠标键位释放事件
     */
    fun <T : Region> T.release(function: (MouseEvent) -> Unit): T {
        setOnMouseReleased {
            function.invoke(it)
        }
        return this
    }

    /**
     * 此方法的调用优先级高于setOnMouseClicked
     */
    fun <T : ButtonBase> T.clickBtn(function: (ActionEvent) -> Unit): T {
        setOnAction {
            function.invoke(it)
        }
        return this
    }

    fun <T:Node> T.focus(bool: Boolean): T {
        isFocusTraversable = bool
        return this
    }

    fun <T:Node> T.marginAp(left: Number, top: Number, right: Number, bottom: Number): T {
        if (top != -1)
            AnchorPane.setTopAnchor(this, top.toDouble())
        if (left != -1)
            AnchorPane.setLeftAnchor(this, left.toDouble())
        if (bottom != -1)
            AnchorPane.setBottomAnchor(this, bottom.toDouble())
        if (right != -1)
            AnchorPane.setRightAnchor(this, right.toDouble())
        return this
    }

    /**
     * 设置外边距，注意此方法不能在apply中调用
     */
    fun <T:Node> T.margin(left: Number, top: Number, right: Number, bottom: Number): T {
        when (parent.javaClass.simpleName) {
            "HBox" -> HBox.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
            "VBox" -> VBox.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
            "AnchorPane" -> {
                AnchorPane.setTopAnchor(this, top.toDouble())
                AnchorPane.setLeftAnchor(this, left.toDouble())
                AnchorPane.setBottomAnchor(this, bottom.toDouble())
                AnchorPane.setRightAnchor(this, right.toDouble())
            }
            "BorderPane" -> {
                BorderPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
            }
            "FlowPane" -> {
                FlowPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
            }
            "StackPane" -> {
                StackPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
            }
        }
        return this
    }

    fun <T:Node> T.alignBp(pos: Pos): T {
        BorderPane.setAlignment(this, pos)
        return this
    }

    fun <T:Node> T.marginHb(left: Number, top: Number, right: Number, bottom: Number): T {
        HBox.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
        return this
    }

    fun <T:Node> T.marginVb(left: Number, top: Number, right: Number, bottom: Number): T {
        VBox.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
        return this
    }

    fun <T:Node> T.marginBp(left: Number, top: Number, right: Number, bottom: Number): T {
        BorderPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
        return this
    }

    fun <T:Node> T.marginSp(left: Number, top: Number, right: Number, bottom: Number): T {
        StackPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
        return this
    }

    fun <T:Node> T.marginFp(left: Number, top: Number, right: Number, bottom: Number): T {
        FlowPane.setMargin(this, Insets(top.toDouble(), right.toDouble(), bottom.toDouble(), left.toDouble()))
        return this
    }

    fun <T:Node> T.show(): T {
        if (!isVisible) isVisible = true
        if (!isManaged) isManaged = true
        return this
    }

    fun <T:Node> T.hide(): T {
        isVisible = false
        return this
    }

    fun <T:Node> T.gone(): T {
        isManaged = false
        return this
    }

    fun <T:Node> T.alpha(alpha: Double): T {
        opacity = 1 - alpha
        return this
    }

    fun <T : Node> GridPane.setListV(row: Int, list: List<T>) {
        list.forEachIndexed { index, t ->
            add(t, index % row, index / row)
        }
    }

    fun <T:Pane> T.addChild(child:Node): T {
        children.add(child)
        return this
    }

    fun <T:Pane> T.addChildren(vararg child:Node): T {
        children.addAll(child)
        return this
    }

    fun <T:Pane> T.addChildren(nodes:List<Node>): T {
        children.addAll(nodes)
        return this
    }

    val ActionEvent.text get() = (source as Labeled).text


}