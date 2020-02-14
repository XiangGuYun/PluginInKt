package base.utils

import javafx.application.Platform

interface PlatformUtils {

    fun runOnMainThread(callback:()->Unit){
        Platform.runLater {
            callback.invoke()
        }
    }

    fun finish(){
        Platform.exit()
    }

}