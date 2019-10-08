package ru.netology.saturn33.homework.hw10.dto

enum class PostType {
    POST, EVENT, REPOST, YOUTUBE, AD
}

class Post(
    val id: Long,
    val author: String,
    val created: Long,
    val content: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var comments: Int = 0,
    var commentedByMe: Boolean = false,
    var shares: Int = 0,
    var sharedByMe: Boolean = false,
    val type: PostType = PostType.POST,
    val source: Post? = null,//for repost
    val video: Video? = null,//for youtube
    val location: Location? = null,//for event
    val link: String? = null//for ad
)
