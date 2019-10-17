package ru.netology.saturn33.homework.hw12.dto

import ru.netology.saturn33.homework.hw12.API_BASE_URL

enum class AttachmentType {
    IMAGE,
    AUDIO,
    VIDEO
}

data class AttachmentModel(
    val id: String,
    val mediaType: AttachmentType
) {
    val url
        get() = "${API_BASE_URL}static/$id"
}
