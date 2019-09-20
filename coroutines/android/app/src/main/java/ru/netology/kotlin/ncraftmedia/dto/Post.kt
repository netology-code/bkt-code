package ru.netology.kotlin.ncraftmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String? = null,
    val created: Int,
    var likedByMe: Boolean = false
)

