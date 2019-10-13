package ru.netology.saturn33.homework.hw11.dto

data class PostRequestDto(
    val id: Long = 0,
    val postType: PostType = PostType.POST,
    val content: String? = null//for post, event, repost, youtube
)
