package ru.netology.kotlin.nullable

enum class PostType(value: String) {
    POST("post"), REPOST("repost"), REPLY("reply")
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