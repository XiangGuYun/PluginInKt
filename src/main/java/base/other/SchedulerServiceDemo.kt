package base.other

import base.constant.Window
import javafx.concurrent.ScheduledService
import javafx.concurrent.Task
import javafx.util.Duration
import sample.base.BaseApp

/**
 * 类似于Android中的AsyncTask
 */
class SchedulerServiceDemo : BaseApp() {

    private lateinit var service: ScheduledService<Int>

    override fun init(window: Window) {
        window.size(100,100)
        var time = 60

        service = object : ScheduledService<Int>(){
            override fun createTask(): Task<Int> {
                return object :Task<Int>(){
                    override fun call(): Int {
                        "call ${Thread.currentThread().name}".pln()
                        return --time
                    }

                    override fun updateValue(value: Int?) {
                        "update ${Thread.currentThread().name} $value".pln()
                        if(value==40) stopService()
                    }
                }
            }
        }
        service.delay = Duration(1000.0)
        service.period = Duration(1000.0)
        service.start()
    }

    fun stopService(){
        service.cancel()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SchedulerServiceDemo::class.java)
        }
    }
}