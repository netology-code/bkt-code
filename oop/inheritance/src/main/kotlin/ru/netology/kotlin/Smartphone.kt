package ru.netology.kotlin

class Smartphone(number: String): Phone(number) {
    override fun call(to: String) {
        when {
            to.startsWith("whatsapp:") -> println("Whatsapp call to $to")
            to.startsWith("telegram:") -> println("Telegram call to $to")
            to.startsWith("skype:") -> println("Skype call to $to")
            else -> super.call(to)
        }
    }
}
