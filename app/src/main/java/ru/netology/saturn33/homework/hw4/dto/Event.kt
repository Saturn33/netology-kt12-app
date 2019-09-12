package ru.netology.saturn33.homework.hw4.dto

class Event(
    id: Long,
    author: String,
    created: Long,
    content: String,
    val address: String,
    val location: Location,
    likes: Int = 0,
    likedByMe: Boolean = false,
    comments: Int = 0,
    commentedByMe: Boolean = false,
    shares: Int = 0,
    sharedByMe: Boolean = false
) : Post(
    id,
    author,
    created,
    content,
    likes,
    likedByMe,
    comments,
    commentedByMe,
    shares,
    sharedByMe
)