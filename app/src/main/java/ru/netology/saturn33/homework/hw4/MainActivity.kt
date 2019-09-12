package ru.netology.saturn33.homework.hw4

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.saturn33.homework.hw4.dto.Event
import ru.netology.saturn33.homework.hw4.dto.Location
import ru.netology.saturn33.homework.hw4.dto.Post
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val now = Date().time
        val post = Event(
            1,
            "ЯЧеловекСОченьДлиннымЛогиномВозможноЭтоЕщёНеВсё",
            now - 8 * Intervals.HOUR.seconds,
            "Мой первый достаточно длинный и информативный пост в ещё не существующей соцсети.",
            "Офис Нетологии", Location(55.7039398,37.6240304,17),
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

        if (post is Event) {
            //отображение изображения с локацией и установка интента на открытие карты
            imgLocation.visibility = View.VISIBLE
            imgLocation.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("geo:${post.location.lat},${post.location.lng}?z=${post.location.zoom}")
                }
                startActivity(intent)
            }
        }

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
