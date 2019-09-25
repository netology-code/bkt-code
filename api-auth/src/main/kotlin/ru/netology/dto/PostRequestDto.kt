package ru.netology.dto

data class PostRequestDto(
    val id: Long,
    val author: String,
    val content: String? = null
)