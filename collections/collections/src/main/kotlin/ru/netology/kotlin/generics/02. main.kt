package ru.netology.kotlin.generics

open class Parent
class Child: Parent()

fun main() {
    val post = Post(1, "Netology", "First post in our network!", 1566408240)
    val user = User(1, "Netology")

    val anyPair = AnyPair(user, post)
    if (anyPair.second is Post) {
        println(anyPair.second.content)
    }

    val genericPair = GenericPair(user, post)
    println(genericPair.second.content)

    val genericPairWithNull: GenericPair<User?, Post?> = GenericPair(null, post)
    println(genericPairWithNull.second?.content)

    val genericPairNullable: GenericPairNullable<User, Post> = GenericPairNullable(null, null)
    val first = genericPairNullable.first

    val monoPair = MonoPair(1F, 1L)
    println(monoPair)

    val inherited = UserPostPairInherited(user, post)
}