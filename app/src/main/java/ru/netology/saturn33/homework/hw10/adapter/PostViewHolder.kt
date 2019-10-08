package ru.netology.saturn33.homework.hw10.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.footer.view.*
import kotlinx.android.synthetic.main.part_main_content.view.*
import kotlinx.android.synthetic.main.part_social_buttons.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.Utils
import ru.netology.saturn33.homework.hw10.dto.PostModel
import ru.netology.saturn33.homework.hw10.repositories.Repository
import ru.netology.saturn33.homework.hw10.ui.CreateRepostActivity
import java.io.IOException
import java.util.*

open class PostViewHolder(adapter: PostAdapter, itemView: View) :
    BaseViewHolder(adapter, itemView) {
    init {
        with(itemView) {
            imgLike.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentPosition = adapterPosition
                    val item = adapter.list[adapterPosition]
                    if (item.likeActionPerforming) {
                        context.toast(context.getString(R.string.like_performing))
                    }
                    else {
                        GlobalScope.launch(Dispatchers.Main) {
                            try {
                                item.likeActionPerforming = true
                                adapter.notifyItemChanged(currentPosition)
                                val response = if (item.likedByMe) {
                                    Repository.dislike(item.id)
                                } else {
                                    Repository.like(item.id)
                                }
                                if (response.isSuccessful) {
                                    item.updatePost(response.body()!!)
                                }
                                adapter.notifyItemChanged(currentPosition)
                            }
                            catch (e: IOException) {
                                context.toast(context.getString(R.string.like_error))
                            }
                            item.likeActionPerforming = false
                        }
                    }
                }
            }
            imgRepost.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    context.startActivity<CreateRepostActivity>("postId" to item.id)
                }
            }
            /*
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
            */
//            imgLocation.setOnClickListener(null)
            imgHide.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    adapter.list.removeAt(adapterPosition)
                    adapter.notifyItemRemoved(adapterPosition)
                }
            }
        }
    }

    override fun bind(post: PostModel) {
        with(itemView) {
            val now = Date().time
            tvCreated.text = Utils.publishedAgo((now - post.created) / 1000)
            author.text = post.author.username
            content.text = post.content

            updateInfo(
                imgLike, txtLike, post.likedByMe, post.likes,
                R.drawable.ic_like_active_24dp, R.drawable.ic_like_inactive_24dp, post.likeActionPerforming, R.drawable.ic_like_pending_24dp
            )

            updateInfo(
                imgRepost, txtRepost, post.repostedByMe, post.reposts,
                R.drawable.ic_repost_active_24dp, R.drawable.ic_repost_inactive_24dp, post.repostActionPerforming, R.drawable.ic_repost_pending_24dp
            )
/*

            updateInfo(
                imgComment, txtComment, post.commentedByMe, post.comments,
                R.drawable.ic_comment_active_24dp, R.drawable.ic_comment_inactive_24dp
            )

            updateInfo(
                imgShare, txtShare, post.sharedByMe, post.shares,
                R.drawable.ic_share_active_24dp, R.drawable.ic_share_inactive_24dp
            )

*/
//            imgLocation.visibility = View.GONE
        }
    }
}