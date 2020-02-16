package base.control

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label

class DemoCtrl() {
    @FXML
    lateinit var btn1:Button

    @FXML
    fun initialize(){
        println("init   "+btn1.text)
    }

    fun click(actionEvent: ActionEvent) {
        println("666666")
    }

}