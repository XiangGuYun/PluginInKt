package base

import base.utils.CommonUtils
import com.sun.jna.Pointer
import com.sun.jna.ptr.PointerByReference

class ConsoleApp:CommonUtils{
    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            val pbr = PointerByReference()
            pbr.pointer = Pointer(1)
            println(pbr.pointer.getInt(1))

        }
    }
}