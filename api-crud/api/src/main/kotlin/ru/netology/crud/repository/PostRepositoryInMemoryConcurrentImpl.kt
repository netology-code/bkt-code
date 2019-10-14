package ru.netology.crud.repository

import kotlinx.atomicfu.atomic
import ru.netology.crud.model.PostModel
import java.util.concurrent.CopyOnWriteArrayList

class PostRepositoryInMemoryConcurrentImpl : PostRepository {
    private var nextId = atomic(0L)
    private val items = CopyOnWriteArrayList<PostModel>()

    override suspend fun getAll(): List<PostModel> {
        return items.reversed()
    }

    override suspend fun getById(id: Long): PostModel? {
        return items.find { it.id == id }
    }

    override suspend fun save(item: PostModel): PostModel {
        return when (val index = items.indexOfFirst { it.id == item.id }) {
            -1 -> {
                val copy = item.copy(id = nextId.incrementAndGet())
                items.add(copy)
                copy
            }
            else -> {
                items[index] = item
                item
            }
        }
    }

    override suspend fun removeById(id: Long) {
        items.removeIf { it.id == id }
    }

    override suspend fun likeById(id: Long): PostModel? {
        return when (val index = items.indexOfFirst { it.id == id }) {
            -1 -> null
            else -> {
                val item = items[index]
                val copy = item.copy(likes = item.likes + 1) // copy = id -> id
                items[index] = copy
                copy
            }
        }
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}