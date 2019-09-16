package ru.netology.kotlin.collections

enum class PostType(value: String) {
    POST("post"), REPOST("repost"), REPLY("reply")
}

class Post(
    val id: Long,
    val author: String,
    val content: String? = null,
    val created: Int,
    val likedByMe: Boolean = false,
    val postType: PostType = PostType.POST,
    val source: Post? = null
)

