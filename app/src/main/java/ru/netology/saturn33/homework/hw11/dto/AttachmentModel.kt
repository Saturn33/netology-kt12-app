package ru.netology.saturn33.homework.hw11.dto

enum class AttachmentType {
    IMAGE,
    AUDIO,
    VIDEO
}

data class AttachmentModel(
    val id: String,
    val url: String,
    val type: AttachmentType
)
