package ru.netology.saturn33.homework.hw5

enum class Intervals(val seconds: Long) {
    SECOND(1L),
    MINUTE(60L),
    HOUR(60L * 60L),
    DAY(60L * 60L * 24L)
}