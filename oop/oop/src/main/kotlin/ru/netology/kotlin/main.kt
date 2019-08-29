package ru.netology.kotlin

fun main() {
    val iphone = Smartphone("Apple", "10x", height = 5.7, width = 2.2)
    iphone.voiceLevel = 100
    println(iphone.voiceLevel)
    println(iphone.diagonal)
}
