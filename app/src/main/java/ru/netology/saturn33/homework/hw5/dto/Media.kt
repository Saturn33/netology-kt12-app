package ru.netology.saturn33.homework.hw5.dto

open class Media(
    val id: Int,
    val url: String,//ссылка на контент
    val caption: String?,//подпись
    var likes: Int = 0//количество лайков
)
