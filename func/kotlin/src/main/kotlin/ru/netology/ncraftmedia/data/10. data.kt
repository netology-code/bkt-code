package ru.netology.ncraftmedia.data

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val likedByMe: Boolean = false,
    val isRepost: Boolean = false
)

fun main() {
    val post = Post(1, "Netology", "First Post")
    val (id, author) = post
    val repost = post.copy(id = 2, isRepost = true)
}