package ru.netology.kotlin.ncraftmedia.dto

class Post(
    val id: Long,
    val author: String,
    val content: String,
    val created: String,
    var likedByMe: Boolean = false
)