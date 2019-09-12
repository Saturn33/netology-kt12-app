package ru.netology.saturn33.homework.hw4.dto

open class Media(
    val id: Int,
    val url: String,//ссылка на контент
    val caption: String?,//подпись
    var likes: Int = 0//количество лайков
)
