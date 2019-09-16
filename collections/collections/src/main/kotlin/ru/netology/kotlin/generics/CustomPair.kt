package ru.netology.kotlin.generics

class AnyPair(val first: Any, val second: Any)

class UserPostPair(val user: User, val post: Post)

class GenericPair<A, B>(var first: A, var second: B)
class GenericPairNullable<A, B>(var first: A?, var second: B?)

class MonoPair<T>(val first: T, val second: T)