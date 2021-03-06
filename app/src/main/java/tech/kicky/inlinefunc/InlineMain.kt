package tech.kicky.inlinefunc

/**
 * 内联测试
 * author: yidong
 * 2021/2/17
 */

fun main() {
    greeting({
        println("Before")
    }, {
        println("After")
    })
    println("Finish Greeting")
}

private inline fun greeting(before: () -> Unit, crossinline after: () -> Unit) {
    before()
    println("Hello")
    wrapAfter { after() }
}

private fun wrapAfter(after: () -> Unit) {
    println("Before After")
    after()
}


