package ru.netology.ncraftmedia.lambda

typealias ClickHandler = (view: View) -> Unit

class View(var onClick: ClickHandler) {
    fun performClick() {
        onClick(this)
    }
}

fun main() {
    val view = View({ view -> println("clicked") })
//    val handler = { view: View -> println("clicked") }
//     val view = View { view -> println("clicked") }
//     val view = View { _ -> println("clicked") }
//     val view = View { println("clicked") }
//    val view = View {
//        println(it)
//        println("clicked")
//    }
    view.performClick()
}
