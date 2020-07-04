package app.applet

import javafx.scene.image.Image
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import java.io.File


object CopyUtils {

    /**
     * 复制图像到剪贴板
     * @param image Image
     */
    fun copyImage(image:Image) {
        val clipboard: Clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putImage(image)
        clipboard.setContent(content)
    }

    /**
     * 复制文件到剪贴板
     * @param files Array<out File>
     */
    fun copyFiles(vararg files: File){
        val clipboard: Clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putFiles(files.toList())
        clipboard.setContent(content)
    }

    fun copyString(string: String){
        val clipboard: Clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(string)
        clipboard.setContent(content)
    }

}