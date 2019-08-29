package ru.netology.kotlin

fun main() {
    val iphone = Smartphone("+79275XXXXXX")
    iphone.call("whatsapp:+79275YYYYYY")
    iphone.call("+79273ZZZZZZ")
    iphone.sendSms("+79273ZZZZZZ", "Where are you?")
}
