package base

import javafx.scene.control.Alert

interface DialogUtils {
    
    fun alert(content:Any, title:String = "提示"){
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = content.toString()
        alert.showAndWait()
    }
    
}