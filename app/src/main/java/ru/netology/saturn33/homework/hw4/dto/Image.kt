package ru.netology.saturn33.homework.hw4.dto

class Image(
    id: Int,
    url: String,
    caption: String,
    likes: Int = 0,
    val width: Int,//ширина
    val height: Int//высота
) : Media(id, url, caption, likes)
