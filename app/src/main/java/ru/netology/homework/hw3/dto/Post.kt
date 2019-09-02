package ru.netology.homework.hw3.dto

class Post(
    val id: Long,
    val author: String,
    val created: Long,
    val content: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val comments: Int = 0,
    val commentedByMe: Boolean = false,
    val shares: Int = 0,
    val sharedByMe: Boolean = false
)
