package app

import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.reflect.KProperty

// 定义包含属性委托的类
class Example {
    var p: String by Delegate()
}

// 委托的类
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, 这里委托了 ${property.name} 属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
    }
}

var flat = Random.nextInt(1, 100)

val string: String by lazy {
    if(flat>50)
        "大于50"
    else
        "不大于50"
}

var obValue: String by Delegates.observable("初始值"){
    property, oldValue, newValue ->
    println("旧值：$oldValue -> 新值：$newValue")
}

fun main(args: Array<String>) {
    println(obValue)
    obValue = "新址"
    println(obValue)
}