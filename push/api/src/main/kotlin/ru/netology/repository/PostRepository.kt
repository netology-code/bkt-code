package ru.netology.repository

import ru.netology.model.PostModel

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Long): PostModel?
    suspend fun getByIds(ids: Collection<Long>): List<PostModel>
    suspend fun save(item: PostModel): PostModel
    suspend fun removeByIdAndOwnerId(id: Long, ownerId: Long)
    suspend fun likeById(id: Long, myId: Long): PostModel?
    suspend fun dislikeById(id: Long, myId: Long): PostModel?
}