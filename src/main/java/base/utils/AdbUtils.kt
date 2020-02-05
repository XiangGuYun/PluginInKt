package base.utils

/**
 * Adb工具类
 *
 * 查看当前运行的APP的包名和入口类信息
 * adb shell dumpsys window | findstr mCurrentFocus
 *
 * 列举当前连接的设备
 * adb devices
 *
 * 启动App
 * adb shell am start 包名/完整Activity路径
 */
interface AdbUtils {

    val ADB_PATH get() =  "C:\\Users\\Administrator\\AppData\\Local\\Android\\Sdk\\platform-tools\\"

    /**
     * 启动App
     */
    fun runAndroidApp(packageName:String, mainActivity:String, adbDir:String = ADB_PATH){
        Runtime.getRuntime().exec("${adbDir}adb shell am start $packageName/$mainActivity")
    }


    /**
     * 在指定设备上启动App
     */
    fun runAndroidAppEx(packageName:String, mainActivity:String, deviceName:String, adbDir:String = ADB_PATH){
        Runtime.getRuntime().exec("${adbDir}adb -s $deviceName shell am start $packageName/$mainActivity")
    }

}