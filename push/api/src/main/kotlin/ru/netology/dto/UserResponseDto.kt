package ru.netology.dto

import ru.netology.model.PostModel
import ru.netology.model.UserModel

data class UserResponseDto(val id: Long, val username: String) {
    companion object {
        fun fromModel(model: UserModel) = UserResponseDto(
            id = model.id,
            username = model.username
        )

        fun unknown() = UserResponseDto(
            id = 0,
            username = "unknown"
        )
    }
}
