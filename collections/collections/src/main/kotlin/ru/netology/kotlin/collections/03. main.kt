package ru.netology.kotlin.collections

fun main() {
    val list = listOf(
        Post(1, "Netology", "First post in our network!", 1566408240),
        Post(2, "Netology", "Our network is growing!", 1566418240)
    )

    val iterator = list.listIterator()
    while (iterator.hasNext()) {
        val post = iterator.next()
        println(post)
    }

    for (post in list) {
        println(post)
    }

    list.forEach {
        println(it)
    }
}