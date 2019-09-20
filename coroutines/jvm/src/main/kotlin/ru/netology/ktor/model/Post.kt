package ru.netology.ktor.model


data class Post(
    val id: Long,
    val author: String,
    val content: String? = null,
    val created: Int,
    var likedByMe: Boolean = false,
    val postType: PostType = PostType.POST
)

enum class PostType {
    POST, REPOST
}
