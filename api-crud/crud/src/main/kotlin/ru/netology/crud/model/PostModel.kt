package ru.netology.crud.model


data class PostModel(
    val id: Long,
    val author: String,
    val content: String? = null,
    val likes: Int = 0,
    val postType: PostType = PostType.POST
)

enum class PostType {
    POST, REPOST
}
