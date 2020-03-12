package app

import base.utils.CommonUtils


object Test : CommonUtils {
    @JvmStatic
    fun main(args: Array<String>) {
        val i: Int = 127
        val a: Int? = i
        val b: Int? = i
        (a === b).pln()
    }
}