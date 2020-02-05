package test

object AdbTest {

    val ADB_PATH = "C:\\Users\\Administrator\\AppData\\Local\\Android\\Sdk\\platform-tools\\"

    @JvmStatic
    fun main(args: Array<String>) {
        val cmdStr = "${ADB_PATH}adb devices"
        val run = Runtime.getRuntime()
        val process = run.exec(cmdStr)
        println(process.inputStream.bufferedReader().readText())
    }
}