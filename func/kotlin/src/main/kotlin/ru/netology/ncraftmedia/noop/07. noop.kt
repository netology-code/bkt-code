package ru.netology.ncraftmedia.noop

typealias ClickHandler = (view: View) -> Unit

class View(var onClick: ClickHandler = {}) {
    fun performClick() {
        onClick(this)
    }
}

fun main() {
    val view = View()
    view.performClick()
    view.onClick = { println("clicked") }
    view.performClick()
}
