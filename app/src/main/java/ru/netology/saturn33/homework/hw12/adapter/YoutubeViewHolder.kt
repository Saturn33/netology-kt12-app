package ru.netology.saturn33.homework.hw12.adapter
/*
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_youtube.view.*

class YoutubeViewHolder(adapter: PostAdapter, itemView: View) : PostViewHolder(adapter, itemView) {
    init {
        with(itemView) {
            contentMedia.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    item.video?.let { video ->
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse(video.url)
                        }.also {
                            itemView.context.startActivity(it)
                        }

                    }
                }
            }
        }
    }
}
*/