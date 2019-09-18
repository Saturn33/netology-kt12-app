package ru.netology.saturn33.homework.hw5

import ru.netology.saturn33.homework.hw5.dto.Location
import ru.netology.saturn33.homework.hw5.dto.Post
import ru.netology.saturn33.homework.hw5.dto.PostType
import ru.netology.saturn33.homework.hw5.dto.Video
import java.util.*
import kotlin.random.Random

object DataGenerator {
    fun getInitialPosts(): MutableList<Post> {

        val now = Date().time
        val media = Video(
            22,
            "https://www.youtube.com/watch?v=G9VrVUoQ0j8",
            "ANDROID 10 is Here!",
            2200,
            916
        )

        val coroutinesEvent = Post(
            4,
            "Администратор",
            now - 1 * Intervals.DAY.seconds * 1000,
            "Приглашаем всех на следующую лекцию про Coroutines!",
            1, true,
            3, true,
            10, false,
            type = PostType.EVENT,
            location = Location(55.7039398, 37.6240304, 17, "Офис Нетологии")
        )

        val list = mutableListOf(
            Post(
                6,
                "Студент2",
                now - 12 * Intervals.SECOND.seconds * 1000,
                "Пора бы уже выходить, опаздываем...",
                0,
                false,
                0,
                false,
                0,
                false,
                PostType.REPOST,
                source = coroutinesEvent
            ),
            Post(
                5,
                "Студент1",
                now - 8 * Intervals.HOUR.seconds * 1000,
                "Собираемся на лекцию, уже скоро, зовите друзей!",
                1,
                true,
                0,
                false,
                2,
                false,
                PostType.REPOST,
                source = coroutinesEvent
            ),
            coroutinesEvent,
            Post(
                3,
                "Любитель видео",
                now - 2 * Intervals.DAY.seconds * 1000,
                "Зацените новое видео про Android 10!",
                48,
                true,
                35,
                true,
                1,
                false,
                PostType.YOUTUBE,
                video = media
            ),
            Post(
                2,
                "Рекламодатель",
                now - 14 * Intervals.DAY.seconds * 1000,
                "Пока ещё цены на рекламу низкие, размещу: [ПРОДАМ МОПЕД]",
                5,
                false,
                0,
                false,
                95,
                true,
                PostType.AD,
                link = "https://lurkmore.to/%D0%9C%D0%BE%D1%82%D0%BE%D1%80%D0%BE%D0%BB%D0%BB%D0%B5%D1%80"
            ),
            Post(1, "Тестер", now - 400 * Intervals.DAY.seconds * 1000, "Тесттест123")
        )
        list.sortByDescending { post -> post.created }
        return list
    }

    fun getSomePosts(list: MutableList<Post>, count: Int) {
        val lastId = list.maxBy { item -> item.id }?.id ?: 0
        val now = Date().time
        repeat(
            count
        ) { i ->
            val postId = (lastId + 1 + i)
            val likesCnt = Random.nextInt(10)
            val commentsCnt = Random.nextInt(7)
            val sharesCnt = Random.nextInt(3)
            list.add(
                0,
                Post(
                    postId,
                    "Тестер $postId",
                    now - postId * Intervals.DAY.seconds * 1000,
                    "Тестовое сообщение $postId",
                    likes = likesCnt,
                    comments = commentsCnt,
                    shares = sharesCnt,
                    likedByMe = if (likesCnt == 0) false else Random.nextBoolean(),
                    commentedByMe = if (commentsCnt == 0) false else Random.nextBoolean(),
                    sharedByMe = if (sharesCnt == 0) false else Random.nextBoolean()
                )
            )
        }
        //сортировка по времени добавления поста после добавления новых постов сделана для простоты;
        //понятно, что придётся уведомлять об изменении всего датасета или прибегать к другим ухищрениям (DiffUtil или подобное),
        //или же делать добавление более осознанным с поиском в какое место списка нужно добавить и запоминанием этих мест для того, чтобы уведомить о добавлении только этих элементов
        list.sortByDescending { post -> post.created }
    }
}