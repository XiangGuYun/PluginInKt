package base.utils

interface CommonUtils {
    /**
     * 运行应用程序
     */
    fun runApp(appPath:String): Process? {
        val runtime  = Runtime.getRuntime()
        val process = runtime.exec(appPath)
        return process
    }

    fun Thread.startThread(): Thread {
        this.start()
        return this
    }
}