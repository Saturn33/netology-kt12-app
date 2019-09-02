package ru.netology.homework.hw3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.homework.hw3.dto.Post
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val now = Date().time
        val post = Post(
            1,
            "ЯЧеловекСОченьДлиннымЛогиномВозможноЭтоЕщёНеВсё",
            now - 8 * Utils.HOUR,
            "Мой первый достаточно длинный и информативный пост в ещё не существующей соцсети.",
            0, false,
            10, true,
            100500, false
        )

        tvCreated.text = Utils.publishedAgo(now - post.created)
        tvAuthor.text = post.author
        tvContent.text = post.content

        if (post.likedByMe) {
            tgLike.setImageResource(R.drawable.ic_like_active_24dp)
            tvLike.setTextColor(Color.RED)
        }
        tvLike.text = if (post.likes > 0) post.likes.toString() else ""

        if (post.commentedByMe) {
            tgComment.setImageResource(R.drawable.ic_comment_active_24dp)
            tvComment.setTextColor(Color.RED)
        }
        tvComment.text = if (post.comments > 0) post.comments.toString() else ""

        if (post.sharedByMe) {
            tgShare.setImageResource(R.drawable.ic_share_active_24dp)
            tvShare.setTextColor(Color.RED)
        }
        tvShare.text = if (post.shares > 0) post.shares.toString() else ""
    }
}
