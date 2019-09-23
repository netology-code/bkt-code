package ru.netology.crud.repository

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import ru.netology.crud.model.PostModel

class PostRepositoryInMemorySingleThreadedImpl : PostRepository {
    private var nextId = 1L
    private val items = mutableListOf<PostModel>()
    private val context = newSingleThreadContext("PostRepository")

    override suspend fun getAll(): List<PostModel> {
        return withContext(context) {
            items.reversed()
        }
    }

    override suspend fun getById(id: Long): PostModel? {
        return withContext(context) {
            items.find { it.id == id }
        }
    }

    override suspend fun save(item: PostModel): PostModel {
        return withContext(context) {
            when (val index = items.indexOfFirst { it.id == item.id }) {
                -1 -> {
                    val copy = item.copy(id = nextId++)
                    items.add(copy)
                    copy
                }
                else -> {
                    items[index] = item
                    item
                }
            }
        }
    }

    override suspend fun removeById(id: Long) {
        withContext(context) {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Long): PostModel? {
        return withContext(context) {
            when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(likes = item.likes + 1)
                    items[index] = copy
                    copy
                }
            }
        }
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}