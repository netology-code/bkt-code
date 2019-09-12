package ru.netology.ncraftmedia.anonymous

class View(var onClick: (view: View) -> Unit) {
    fun performClick() {
        onClick(this)
    }
}

fun main() {
    val handler = fun(view: View) { println("clicked")}
    val view = View(handler)
    view.performClick()
}