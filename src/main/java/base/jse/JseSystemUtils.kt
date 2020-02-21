package base.jse

import java.io.File

interface JseSystemUtils{

    /**
     * 获取用户名
     */
    fun getUserName(): String {
        return System.getProperty("user.name")
    }

    /**
     * 获取用户主页
     */
    fun getUserHome():String {
        return System.getProperty("user.home")
    }

    /**
     * 获取桌面路径（Windows平台）
     */
    fun getUserDesktop():String {
        return System.getProperty("user.home")+ File.separator+"Desktop"
    }

    /**
     * 获取操作系统版本
     */
    fun getOsVersion():String {
        return System.getProperty("os.version")+ File.separator+"Desktop"
    }

    /**
     * 获取操作系统名称
     */
    fun getOsName():String{
        return System.getProperty("os.name")
    }

    /**
     * 获取项目根路径
     */
    fun getProjectPath():String{
        return System.getProperty("user.dir")
    }

    /**
     * 获取Jre的bin目录，此方法可用来查找相关DLL文件
     */
    fun getJreBinPath():String{
        return System.getProperty("sun.boot.library.path")
    }

    /**
     * 获取JAVA_HOME环境变量的路径1
     */
    fun getJavaHome():String{
        return System.getProperty("java.home")
    }

    /**
     * 获取JAVA_HOME环境变量的路径2
     */
    fun getJavaHome1():String{
        return System.getenv("JAVA_HOME")
    }

}