package play

import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

class LwjglApp {
    // 窗口句柄
    private var window: Long = 0

    fun run() {
        println("Hello LWJGL " + Version.getVersion() + "!")
        init()
        loop()
        // 释放窗口回调并销毁窗口
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)
        // 终止GLFW并释放错误回调
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)!!.free()
    }

    private fun init() { // 设置一个错误回调。默认实现将在System.err中打印错误消息。
        GLFWErrorCallback.createPrint(System.err).set()
        // 初始化GLFW。在此之前，大多数GLFW函数都不能工作。
        if (!GLFW.glfwInit()) throw IllegalStateException("Unable to initialize GLFW")
        // 配置 GLFW
        GLFW.glfwDefaultWindowHints() // 可选的，当前窗口提示已经是默认的
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE) // 创建后窗口将保持隐藏
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE) // 窗口将是可调整大小的
        // 创建窗口
        window = GLFW.glfwCreateWindow(300, 300, "Hello World!", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")
        // 设置一个键回调。每当一个键被按下、重复或释放时，它都会被调用。
        GLFW.glfwSetKeyCallback(window) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(window, true) // 我们将在渲染循环中检测到这一点
        }
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1) // int*
            val pHeight = stack.mallocInt(1) // int*
            // 获取传递给glfwCreateWindow的窗口大小
            GLFW.glfwGetWindowSize(window, pWidth, pHeight)
            // 获得主监视器的分辨率
            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
            // 居中窗口
            GLFW.glfwSetWindowPos(
                    window,
                    (vidmode!!.width() - pWidth[0]) / 2,
                    (vidmode.height() - pHeight[0]) / 2
            )
        }
        // 使OpenGL上下文处于当前状态
        GLFW.glfwMakeContextCurrent(window)
        // 启用 v-sync
        GLFW.glfwSwapInterval(1)
        // 窗口可见化
        GLFW.glfwShowWindow(window)
    }

    private fun loop() {
        // 这一行对于LWJGL与GLFW的OpenGL上下文或外部管理的任何上下文的互操作非常关键。
        // LWJGL检测当前线程中当前的上下文，创建glabilities实例并使OpenGL绑定可用。
        GL.createCapabilities()
        // 设置清晰的颜色
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
        // 运行呈现循环，直到用户试图关闭窗口或按下ESCAPE键。
        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT) // 清除framebuffer
            GLFW.glfwSwapBuffers(window) // 交换颜色缓冲
            // 轮询窗口事件。上面的键回调将仅在此调用期间调用。
            GLFW.glfwPollEvents()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            LwjglApp().run()
        }
    }
}