package base.utils.jfx

import javafx.collections.FXCollections
import javafx.scene.control.*
import sample.base.ViewUtils

interface DialogUtils : ViewUtils {

    fun alert(content: Any, title: String = "提示"): Alert {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = content.toString()
        alert.show()
        return alert
    }

    fun alertW(content: Any, title: String = "警告") {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = title
        alert.headerText = null
        alert.contentText = content.toString()
        alert.show()
    }

    fun alertE(content: Any, title: String = "错误") {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = null
        alert.contentText = content.toString()
        alert.show()
    }

    fun alertC(content: Any, title: String = "确认") {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = content.toString()
        alert.show()
    }

    /**
     * 显示输入对话框
     */
    fun showInputDialog(text: String = "", defValue: String = "", getResult: (result: String) -> Unit): TextInputDialog {
        val dialog = TextInputDialog(defValue)
                .apply {
                    headerText = null
                    title = text
                    (dialogPane.lookupButton(ButtonType.OK) as Button).clickBN {
                        getResult.invoke(editor.text)
                    }
                }
        dialog.show()
        return dialog
    }

    /**
     * 显示选择对话框
     */
    fun showSelectDialog(text: String = "", defValue: String = "", list: List<String>, getResult: (result: String) -> Unit) {
        ChoiceDialog<String>(defValue, FXCollections.observableArrayList<String>()
                .apply {
                    addAll(list)
                })
                .apply {
                    headerText = null
                    title = text
                    selectedItemProperty().addListener { _, _, newValue ->
                        getResult.invoke(newValue)
                    }
                }.show()
    }

}