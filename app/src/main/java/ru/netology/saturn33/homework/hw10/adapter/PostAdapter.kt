package ru.netology.saturn33.homework.hw10.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.dto.Post
import ru.netology.saturn33.homework.hw10.dto.PostType

val viewTypeToPostType = mutableMapOf<Int, PostType>(
    1 to PostType.POST,
    2 to PostType.REPOST,
    3 to PostType.YOUTUBE,
    4 to PostType.EVENT,
    5 to PostType.AD
)

class PostAdapter(val list: MutableList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewTypeToPostType[viewType]) {
            PostType.POST -> PostViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(R.layout.post_simple, parent, false)
            )
            PostType.EVENT -> EventViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(R.layout.post_event, parent, false)
            )
            PostType.AD -> AdViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(R.layout.post_ad, parent, false)
            )
            PostType.YOUTUBE -> YoutubeViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(R.layout.post_youtube, parent, false)
            )
            PostType.REPOST -> RepostViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_simple,
                    parent,
                    false
                )
            )
            null -> TODO("bad view type")
        }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return viewTypeToPostType.filterValues { list[position].type == it }.keys.first()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as BaseViewHolder) {
            bind(list[position])
        }
    }
}