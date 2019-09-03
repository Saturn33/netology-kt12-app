package ru.netology.homework.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.homework.hw3.dto.Post
import java.util.Date

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
            3, false,
            0, true,
            100, false
        )

        tvCreated.text = Utils.publishedAgo(now - post.created)
        author.text = post.author
        content.text = post.content

        if (post.likedByMe) {
            imgLike.setImageResource(R.drawable.ic_like_active_24dp)
            txtLike.setTextColor(resources.getColor(R.color.colorDigitsActive, null))
        } else {
            imgLike.setImageResource(R.drawable.ic_like_inactive_24dp)
            txtLike.setTextColor(resources.getColor(R.color.colorDigits, null))
        }
        if (post.likes > 0) {
            txtLike.text = post.likes.toString()
            txtLike.visibility = View.VISIBLE
        } else {
            txtLike.visibility = View.GONE
        }


        if (post.commentedByMe) {
            imgComment.setImageResource(R.drawable.ic_comment_active_24dp)
            txtComment.setTextColor(resources.getColor(R.color.colorDigitsActive, null))
        } else {
            imgComment.setImageResource(R.drawable.ic_comment_inactive_24dp)
            txtComment.setTextColor(resources.getColor(R.color.colorDigits, null))
        }
        if (post.comments > 0) {
            txtComment.text = post.comments.toString()
            txtComment.visibility = View.VISIBLE
        } else {
            txtComment.visibility = View.GONE
        }

        if (post.sharedByMe) {
            imgShare.setImageResource(R.drawable.ic_share_active_24dp)
            txtShare.setTextColor(resources.getColor(R.color.colorDigitsActive, null))
        } else {
            imgShare.setImageResource(R.drawable.ic_share_inactive_24dp)
            txtShare.setTextColor(resources.getColor(R.color.colorDigits, null))
        }
        if (post.shares > 0) {
            txtShare.text = post.shares.toString()
            txtShare.visibility = View.VISIBLE
        } else {
            txtShare.visibility = View.GONE
        }
    }
}
