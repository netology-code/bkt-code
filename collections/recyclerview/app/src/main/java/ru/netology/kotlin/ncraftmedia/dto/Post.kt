package ru.netology.kotlin.ncraftmedia.dto

enum class PostType {
    POST, REPOST, REPLY
}

class Post(
    val id: Long,
    val author: String,
    val content: String? = null,
    val created: Int,
    var likedByMe: Boolean = false,
    val postType: PostType = PostType.POST,
    val source: Post? = null
)
