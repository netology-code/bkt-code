package ru.netology.ncraftmedia.`typealias`

typealias ClickHandler = (view: View) -> Unit

class View(var onClick: ClickHandler) {
    fun performClick() {
        onClick(this)
    }
}

fun main() {
    val handler = fun(view: View) { println("clicked")}
    val view = View(handler)
//    val view = View(fun(view: View) { println("clicked") })
    view.performClick()
}