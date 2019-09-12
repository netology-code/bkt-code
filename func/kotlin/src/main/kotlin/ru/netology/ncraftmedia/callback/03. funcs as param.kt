package ru.netology.ncraftmedia.callback

class View(var onClick: (view: View) -> Unit) {
    fun performClick() {
        onClick(this)
    }
}

fun handleClick(view: View) {
    println("clicked")
}

fun main() {
    val view = View(::handleClick)
    view.performClick()
}