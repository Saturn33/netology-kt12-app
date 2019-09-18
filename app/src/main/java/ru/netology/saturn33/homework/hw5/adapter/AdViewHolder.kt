package ru.netology.saturn33.homework.hw5.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_ad.view.*


class AdViewHolder(adapter: PostAdapter, itemView: View) : PostViewHolder(adapter, itemView) {
    init {
        with(itemView) {
            adLink.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    item.link?.let { link ->
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse(link)
                        }.also {
                            itemView.context.startActivity(it)
                        }
                    }
                }
            }
        }
    }
}
