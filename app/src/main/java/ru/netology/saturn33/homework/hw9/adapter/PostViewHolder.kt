package ru.netology.saturn33.homework.hw9.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.footer.view.*
import kotlinx.android.synthetic.main.part_main_content.view.*
import kotlinx.android.synthetic.main.part_social_buttons.view.*
import ru.netology.saturn33.homework.hw9.R
import ru.netology.saturn33.homework.hw9.Utils
import ru.netology.saturn33.homework.hw9.dto.Post
import java.text.SimpleDateFormat
import java.util.*

open class PostViewHolder(adapter: PostAdapter, itemView: View) :
    BaseViewHolder(adapter, itemView) {
    init {
        with(itemView) {
            imgLike.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    item.likedByMe = !item.likedByMe
                    item.likes += if (item.likedByMe) 1 else -1
                    adapter.notifyItemChanged(adapterPosition)
                }
            }
            imgShare.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        val dateFormatted = SimpleDateFormat(
                            "dd.MM.yyyy HH:mm",
                            Locale.US
                        ).format(Date(item.created))
                        putExtra(
                            Intent.EXTRA_TEXT, """
                            ${item.author} (${dateFormatted})
                            
                            ${item.content}
                        """.trimIndent()
                        )
                        type = "text/plain"
                    }.also {
                        itemView.context.startActivity(it)
                    }
                }
            }
            imgLocation.setOnClickListener(null)
            imgHide.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    adapter.list.removeAt(adapterPosition)
                    adapter.notifyItemRemoved(adapterPosition)
                }
            }
        }
    }

    override fun bind(post: Post) {
        with(itemView) {
            val now = Date().time
            tvCreated.text = Utils.publishedAgo((now - post.created) / 1000)
            author.text = post.author
            content.text = post.content

            updateInfo(
                imgLike, txtLike, post.likedByMe, post.likes,
                R.drawable.ic_like_active_24dp, R.drawable.ic_like_inactive_24dp
            )

            updateInfo(
                imgComment, txtComment, post.commentedByMe, post.comments,
                R.drawable.ic_comment_active_24dp, R.drawable.ic_comment_inactive_24dp
            )

            updateInfo(
                imgShare, txtShare, post.sharedByMe, post.shares,
                R.drawable.ic_share_active_24dp, R.drawable.ic_share_inactive_24dp
            )

            imgLocation.visibility = View.GONE
        }
    }
}