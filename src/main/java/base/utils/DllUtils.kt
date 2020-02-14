package base.utils

import com.sun.jna.FunctionMapper
import com.sun.jna.Library
import com.sun.jna.NativeLibrary
import com.sun.jna.Platform
import java.io.File
import java.lang.reflect.Method
import java.util.HashMap

interface DllUtils {

    fun getDll(dllName:String): String {
        return File("").canonicalPath+"/dll/"+dllName
    }

    fun getOptions(map: Map<String, String>) : HashMap<String, Any?>{
        val options = HashMap<String, Any?>()
        options[Library.OPTION_FUNCTION_MAPPER] = UnionFunctionMapper(map)
        return options
    }

    class UnionFunctionMapper(val map: Map<String, String>) : FunctionMapper {
        override fun getFunctionName(library: NativeLibrary, method: Method): String {
            return if (Platform.is64Bit()) { //dll为64位时候，返回原函数名
                method.name
            } else { //dll为32时，返回更改后的函数名
//                val map = HashMap<String, String>()
//                map["AddFunction"] = "?AddFunction@@YAHHH@Z"
                val name = map[method.name]
                name ?: method.name
            }
        }
    }

}