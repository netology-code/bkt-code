package ru.netology.ncraftmedia.ref

class View

fun handleClick(view: View) {
    println("Clicked")
}

fun main() {
    val handler = ::handleClick
    val handlerFull: (view: View) -> Unit = ::handleClick
    val view = View()
    handler(view)
}