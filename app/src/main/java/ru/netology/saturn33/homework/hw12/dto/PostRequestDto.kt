package ru.netology.saturn33.homework.hw12.dto

data class PostRequestDto(
    val id: Long = 0,
    val postType: PostType = PostType.POST,
    val content: String? = null,//for post, event, repost, youtube
    val attachment: AttachmentModel? = null
)
