package ru.netology.saturn33.homework.hw4

object Utils {
    fun publishedAgo(seconds: Long) : String {
        return when (seconds) {
            in Long.MIN_VALUE until 0 -> "неправильное время"
            in 0 until 5 * Intervals.SECOND.seconds -> "только что"
            in 5 * Intervals.SECOND.seconds until 60 * Intervals.SECOND.seconds -> "менее минуты назад"
            in 60 * Intervals.SECOND.seconds until 120 * Intervals.SECOND.seconds -> "минуту назад"
            in 120 * Intervals.SECOND.seconds until 55 * Intervals.MINUTE.seconds -> (seconds / Intervals.MINUTE.seconds).toInt().let { "$it ${declOfNum(it, arrayOf("минуту", "минуты", "минут"))} назад" }
            in 55 * Intervals.MINUTE.seconds until 90 * Intervals.MINUTE.seconds -> "час назад"
            in 90 * Intervals.MINUTE.seconds until 22 * Intervals.HOUR.seconds -> (seconds / Intervals.HOUR.seconds).toInt().let { "$it ${declOfNum(it, arrayOf("час", "часа", "часов"))} назад" }
            in 22 * Intervals.HOUR.seconds until 26 * Intervals.HOUR.seconds -> "день назад"
            in 26 * Intervals.HOUR.seconds until 360 * Intervals.DAY.seconds -> (seconds / Intervals.DAY.seconds).toInt().let { "$it ${declOfNum(it, arrayOf("день", "дня", "дней"))} назад" }
            in 360 * Intervals.DAY.seconds until 370 * Intervals.DAY.seconds -> "год назад"
            else -> "более года назад"
        }
    }

    private fun declOfNum(number: Int, titles: Array<String>): String {
        val cases = arrayOf(2, 0, 1, 1, 1, 2)
        return titles[if (number % 100 in 5..19) 2 else cases[if (number % 10 < 5) number % 10 else 5]]
    }

}