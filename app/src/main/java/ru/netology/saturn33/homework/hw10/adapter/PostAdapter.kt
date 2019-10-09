package ru.netology.saturn33.homework.hw10.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.dto.PostModel
import ru.netology.saturn33.homework.hw10.dto.PostType

val viewTypeToPostType = mapOf<Int, PostType>(
    1 to PostType.POST,
    2 to PostType.REPOST,
/*
    3 to PostType.YOUTUBE,
    4 to PostType.EVENT,
    5 to PostType.AD
*/
    (-1) to PostType.HEADER,
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
            PostType.HEADER -> HeaderViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_load_new,
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

    override fun getItemCount() = list.size + 2

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> viewTypeToPostType.filterValues { PostType.HEADER == it }.keys.first()
            position == list.size + 1 -> viewTypeToPostType.filterValues { PostType.FOOTER == it }.keys.first()
            //TODO remove -1 when swipetorefresh
            else -> viewTypeToPostType.filterValues { list[position - 1].postType == it }.keys.first()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolder)
            with(holder) {
                //TODO remove -1 when swipetorefresh
                bind(list[position - 1])
            }
    }
}