package app

import base.utils.CommonUtils


object Test : CommonUtils{
    @JvmStatic
    fun main(args:Array<String>){
        for (i in 1..100){
            println(100.r(50))
        }
    }
}