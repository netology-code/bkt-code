package ru.netology.dto

import ru.netology.model.AttachmentModel
import ru.netology.model.MediaType

data class AttachmentResponseDto(val id: String, val mediaType: MediaType) {
    companion object {
        fun fromModel(model: AttachmentModel) = AttachmentResponseDto(
            id = model.id,
            mediaType = model.mediaType
        )
    }
}