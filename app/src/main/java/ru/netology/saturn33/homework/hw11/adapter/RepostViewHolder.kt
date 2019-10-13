package ru.netology.saturn33.homework.hw11.adapter

import android.view.View
import kotlinx.android.synthetic.main.part_main_content.view.*
import ru.netology.saturn33.homework.hw11.R
import ru.netology.saturn33.homework.hw11.dto.PostModel

class RepostViewHolder(adapter: PostAdapter, itemView: View) : PostViewHolder(adapter, itemView) {
    override fun bind(post: PostModel) {
        super.bind(post)
        with(itemView) {
            content.text = "${resources.getString(R.string.re)} (${post.source?.content}): ${content.text}"
        }
    }
}
