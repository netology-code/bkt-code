package ru.netology.crud.repository

import ru.netology.crud.model.PostModel

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private val items = mutableListOf<PostModel>()

    override suspend fun getAll(): List<PostModel> {
        return items.reversed()
    }

    override suspend fun getById(id: Long): PostModel? {
        return items.find { it.id == id }
    }

    // id = 0 - создание, id != 0 - обновление
    override suspend fun save(item: PostModel): PostModel {
        return when (val index = items.indexOfFirst { it.id == item.id }) {
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

    override suspend fun removeById(id: Long) {
        items.removeIf { it.id == id }
    }

    override suspend fun likeById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}