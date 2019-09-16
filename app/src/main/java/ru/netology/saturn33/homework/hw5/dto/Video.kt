package ru.netology.saturn33.homework.hw5.dto

class Video(
    id: Int,
    url: String,
    caption: String,
    likes: Int = 0,
    val duration: Int = 0//длительность видео
) : Media(id, url, caption, likes)
