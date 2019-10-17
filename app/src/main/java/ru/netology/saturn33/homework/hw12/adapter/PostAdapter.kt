package ru.netology.saturn33.homework.hw12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.saturn33.homework.hw12.R
import ru.netology.saturn33.homework.hw12.dto.PostModel
import ru.netology.saturn33.homework.hw12.dto.PostType

val viewTypeToPostType = mapOf<Int, PostType>(
    1 to PostType.POST,
    2 to PostType.REPOST,
/*
    3 to PostType.YOUTUBE,
    4 to PostType.EVENT,
    5 to PostType.AD
*/
    (-2) to PostType.FOOTER
)

class PostAdapter(val list: MutableList<PostModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewTypeToPostType[viewType]) {
            PostType.POST -> PostViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(R.layout.post_simple, parent, false)
            )
/*
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
*/
            PostType.REPOST -> RepostViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_simple,
                    parent,
                    false
                )
            )
            PostType.FOOTER -> FooterViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_load_more,
                    parent,
                    false
                )
            )
            null -> TODO("bad view type")
        }

    override fun getItemCount() = list.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            list.size -> viewTypeToPostType.filterValues { PostType.FOOTER == it }.keys.first()
            else -> viewTypeToPostType.filterValues { list[position].postType == it }.keys.first()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolder)
            with(holder) {
                bind(list[position])
            }
    }
}