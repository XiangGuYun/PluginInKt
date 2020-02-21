package play

import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.debug.Grid
import com.jme3.scene.shape.Box
import com.jme3.system.AppSettings

class JmeApp : SimpleApplication() {
    override fun simpleInitApp() {
        val b = Box(1f, 1f, 1f)
        val geom = Geometry("Box", b)
        
        val mat = Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.randomColor())
        geom.material = mat
        rootNode.attachChild(geom)

        val grid = Geometry("floorGrid", Grid(21, 21, 1f)) // 地板
        val material = Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        material.setColor("Color", ColorRGBA.DarkGray)
        grid.setMaterial(material)  //设置材质
        grid.center()  //居中
        rootNode.attachChild(grid)
        
    }

    override fun simpleUpdate(tpf: Float) { //TODO: add update code
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = JmeApp()
            val settings = AppSettings(true)
            settings.title = "你好3D"
            app.setSettings(settings)
            app.start()
        }
    }
}