package ru.netology.service

import io.ktor.features.NotFoundException
import ru.netology.dto.PostRequestDto
import ru.netology.dto.PostResponseDto
import ru.netology.model.PostModel
import ru.netology.repository.PostRepository

class PostService(private val repo: PostRepository) {
    suspend fun getAll(): List<PostResponseDto> {
        return repo.getAll().map { PostResponseDto.fromModel(it) }
    }

    suspend fun getById(id: Long): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun save(input: PostRequestDto): PostResponseDto {
        val model = PostModel(id = input.id, author = input.author, content = input.content)
        return PostResponseDto.fromModel(repo.save(model))
    }
}