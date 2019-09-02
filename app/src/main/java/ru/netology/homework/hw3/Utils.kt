package ru.netology.homework.hw3

object Utils {
    const val SECOND = 1L
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE
    const val DAY = 24 * HOUR

    fun publishedAgo(seconds: Long) : String {
        return when (seconds) {
            in Long.MIN_VALUE until 0 -> "неправильное время"
            in 0 until 5 * SECOND -> "только что"
            in 5 * SECOND until 60 * SECOND -> "менее минуты назад"
            in 60 * SECOND until 120 * SECOND -> "минуту назад"
            in 120 * SECOND until 55 * MINUTE -> (seconds / MINUTE).toInt().let { "$it ${declOfNum(it, arrayOf("минуту", "минуты", "минут"))} назад" }
            in 55 * MINUTE until 90 * MINUTE -> "час назад"
            in 90 * MINUTE until 22 * HOUR -> (seconds / HOUR).toInt().let { "$it ${declOfNum(it, arrayOf("час", "часа", "часов"))} назад" }
            in 22 * HOUR until 26 * HOUR -> "день назад"
            in 26 * HOUR until 360 * DAY -> (seconds / DAY).toInt().let { "$it ${declOfNum(it, arrayOf("день", "дня", "дней"))} назад" }
            in 360 * DAY until 370 * DAY -> "год назад"
            else -> "более года назад"
        }
    }

    fun declOfNum(number: Int, titles: Array<String>): String {
        val cases = arrayOf(2, 0, 1, 1, 1, 2)
        return titles[if (number % 100 in 5..19) 2 else cases[if (number % 10 < 5) number % 10 else 5]]
    }

}