package ru.netology.saturn33.homework.hw4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.saturn33.homework.hw4.dto.Post
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val now = Date().time
        val post = Post(
            1,
            "ЯЧеловекСОченьДлиннымЛогиномВозможноЭтоЕщёНеВсё",
            now - 8 * Intervals.HOUR.seconds,
            "Мой первый достаточно длинный и информативный пост в ещё не существующей соцсети.",
            1, true,
            3, true,
            100, false
        )

        tvCreated.text = Utils.publishedAgo(now - post.created)
        author.text = post.author
        content.text = post.content

        updateInfo(imgLike, txtLike, post.likedByMe, post.likes,
            R.drawable.ic_like_active_24dp, R.drawable.ic_like_inactive_24dp)

        updateInfo(imgComment, txtComment, post.commentedByMe, post.comments,
            R.drawable.ic_comment_active_24dp, R.drawable.ic_comment_inactive_24dp)

        updateInfo(imgShare, txtShare, post.sharedByMe, post.shares,
            R.drawable.ic_share_active_24dp, R.drawable.ic_share_inactive_24dp)

        imgLike.setOnClickListener {
            post.likedByMe = !post.likedByMe
            post.likes += if (post.likedByMe) 1 else -1
            updateInfo(imgLike, txtLike, post.likedByMe, post.likes,
                R.drawable.ic_like_active_24dp, R.drawable.ic_like_inactive_24dp)
        }
    }

    private fun updateInfo(
        imgButton: ImageButton,
        txtView: TextView,
        active: Boolean,
        count: Int,
        activeDrawable: Int,
        inactiveDrawable: Int,
        activeTextColor: Int = R.color.colorDigitsActive,
        inactiveTextColor: Int = R.color.colorDigits
    ) {
        if (active) {
            imgButton.setImageResource(activeDrawable)
            txtView.setTextColor(resources.getColor(activeTextColor, null))
        } else {
            imgButton.setImageResource(inactiveDrawable)
            txtView.setTextColor(resources.getColor(inactiveTextColor, null))
        }
        if (count > 0) {
            txtView.text = count.toString()
            txtView.visibility = View.VISIBLE
        } else {
            txtView.visibility = View.GONE
        }

    }
}
