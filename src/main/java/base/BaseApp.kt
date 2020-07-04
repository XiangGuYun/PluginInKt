package sample.base

import base.constant.*
import base.utils.*
import com.jfoenix.controls.JFXListView
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.media.Media
import javafx.stage.Screen
import javafx.stage.Stage
import java.io.*
import kotlin.system.exitProcess


abstract class BaseApp:Application(), CommonUtils{

    lateinit var mainScene: Scene
    lateinit var window: Window
    lateinit var contentView:Parent
    val screen = Screen.getPrimary()
    val maxX = screen.bounds.maxX
    val maxY = screen.bounds.maxY
    var layoutId = ""

    @Throws(Exception::class)
    override fun start(mainWindow: Stage) {
        this.window = mainWindow

        val annotations = this::class.annotations
        annotations.forEachIndexed { _, it->
            when(it.annotationClass){
                LayoutId::class->{
                    layoutId = (it as LayoutId).id
                }
                AppTitle::class->{
                    window.title = (it as AppTitle).title
                }
                Resizable::class->{
                    window.isResizable = (it as Resizable).resizable
                }
                AppIcon::class->{
                    window.icons.add(Image("image/${(it as AppIcon).iconPath}".file))
                }
                Style::class->{
                    window.initStyle((it as Style).style)
                }
            }
        }

        if(layoutId.isNotEmpty())
            setContentView("layout/$layoutId")

        init(window)
        window.show()

    }

    fun String.toResPath(): String {
        return javaClass.classLoader.getResource(this).path
    }

    fun moveToCenter(width:Number,height: Number){
        window.x = (maxX - width.toDouble())/2
        window.y = (maxY-height.toDouble())/2
    }

    fun v(id:String): Node {
        return contentView.lookup("#$id")
    }

    fun iv(id:String): ImageView {
        return contentView.lookup("#$id") as ImageView
    }

    fun vb(id:String): VBox {
        return contentView.lookup("#$id") as VBox
    }

    fun sp(id:String): StackPane {
        return contentView.lookup("#$id") as StackPane
    }

    fun ta(id:String): TextArea {
        return contentView.lookup("#$id") as TextArea
    }

    fun tf(id:String): TextField {
        return contentView.lookup("#$id") as TextField
    }

    fun btn(id:String):Button {
        return contentView.lookup("#$id") as Button
    }

    fun lb(id:String):Label{
        return contentView.lookup("#$id") as Label
    }

    fun cb(id:String):ComboBox<String>{
        return contentView.lookup("#$id") as ComboBox<String>
    }

    fun ap(id:String): AnchorPane {
        return contentView.lookup("#$id") as AnchorPane
    }

    fun setContentView(name:String){
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        mainScene = Scene(contentView)
        window.scene = mainScene
        window.scene.stylesheets.add("style/styles.css")
    }

    fun setContentView(name:String,width:Number,height: Number){
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        mainScene = Scene(contentView,width.toDouble(),height.toDouble())
        window.scene = mainScene
        window.scene.stylesheets.add("style/styles.css")
        moveToCenter(width,height)
    }


    abstract fun init(window: Window)

    fun scene(name:String,width: Number,height: Number): Scene {
        contentView = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("$name.fxml"))
        return Scene(contentView, width.toDouble(),height.toDouble())
    }

    fun region(name:String): Region {
        return FXMLLoader.load<Region>(javaClass.classLoader.getResource("$name.fxml"))
    }

    /**
     * 添加样式文件，如果之前已经添加过，那么会替换掉之前的文件
     * @param path String
     */
    fun addStyle(path:String){
        window.scene.stylesheets.add("$path.css")
    }

//    fun file(path:String): File {
//        val input = javaClass.getResourceAsStream(path)
//        val reader = input.reader()
//        println(reader.readText())
//        return File(javaClass.getResource(path).toURI())
//    }

    fun file(path:String): InputStreamReader {
        val input = javaClass.getResourceAsStream(path)
        return input.reader()
    }

    fun fileText(path:String): String {
        val input = javaClass.getResourceAsStream(path)
        val reader = input.reader()
        return reader.readText()
    }

    fun <T> ListView<T>.itemClick(func:(T)->Unit){
        selectionModel.selectedItemProperty().addListener { observable, oldValue, newValue ->
           func.invoke(newValue)
        }
    }

    fun <T> ListView<T>.addView(data:T){
       items.add(data)
    }

    fun media(path:String): Media {
        return Media(javaClass.getResource(path).toString())
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(BaseApp::class.java)
        }
    }

    fun openUrl(url:String){
        hostServices.showDocument(url)
    }

    override fun stop() {
        super.stop()
        exitProcess(0)
    }

}