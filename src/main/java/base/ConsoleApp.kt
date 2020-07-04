package base

import base.utils.CommonUtils

class ConsoleApp:CommonUtils{
    companion object{
        @JvmStatic
        fun main(args:Array<String>){
            val hashMap = HashMap<String, String>()
            hashMap.put("1","2")
        }
    }
}