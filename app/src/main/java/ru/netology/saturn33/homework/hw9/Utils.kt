package ru.netology.saturn33.homework.hw9

import android.content.Context
import ru.netology.saturn33.homework.hw9.dto.Post
import kotlin.math.min

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

    fun injectAds(basicList: MutableList<Post>, advList: MutableList<Post>, each: Int = 3) {
        if (each < 1) return
        val adsCount = min(basicList.size / each, advList.size)
        repeat(adsCount) { i -> basicList.add(i + (i + 1) * each, advList[i]) }
    }

    fun isPasswordValid(password: String): Pair<Boolean, String> {
        val min = 6
        val max = 15

        if (password.length < min) return false to "Минимальное количество символов для пароля - $min"
        if (password.length > max) return false to "Максимальное количество символов для пароля - $max"
        if (!Regex("^[a-zA-Z_\\d]+$").matches(password)) return false to "Пароль должен состоять только из латинских символов и цифр"

        return true to ""
    }

    fun isLoginValid(login: String): Pair<Boolean, String> {
        val min = 1
        val max = 10

        if (login.length < min) return false to "Минимальное количество символов для логина - $min"
        if (login.length > max) return false to "Максмиальное количество символов для логина - $max"

        return true to ""
    }

    fun removeUserAuth(ctx: Context) =
        ctx.getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit().remove(
            AUTHENTICATED_SHARED_KEY
        ).commit()

    fun setUserAuth(ctx: Context, token: String) =
        ctx.getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit().putString(
            AUTHENTICATED_SHARED_KEY,
            token
        ).commit()

    fun isAuthenticated(ctx: Context) =
        ctx.getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY,
            ""
        )?.isNotEmpty() ?: false

}
