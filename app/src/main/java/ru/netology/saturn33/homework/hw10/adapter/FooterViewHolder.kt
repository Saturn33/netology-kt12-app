package ru.netology.saturn33.homework.hw10.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_load_more.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw10.PAGE_SIZE
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.repositories.Repository

class FooterViewHolder(val adapter: PostAdapter, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    init {
        with(itemView) {
            btnLoadMore.setOnClickListener {
                btnLoadMore.isEnabled = false
                progressBarMore.isVisible = true
                GlobalScope.launch(Dispatchers.Main) {
                    val response =
                        Repository.getPostsBefore(if (adapter.list.isEmpty()) 0 else adapter.list.last().id)
                    progressBarMore.isVisible = false
                    btnLoadMore.isEnabled = true

                    if (response.isSuccessful) {
                        val newItems = response.body()!!
                        if (newItems.size > 0) {
                            val oldLastIndex = adapter.list.lastIndex
                            adapter.list.addAll(newItems)
                            //TODO make +1 when swipetorefresh
                            adapter.notifyItemRangeInserted(oldLastIndex + 2, newItems.size)
                        }
                        if (newItems.size < PAGE_SIZE) {
                            btnLoadMore.isEnabled = false
                            btnLoadMore.text = context.getString(R.string.no_more_posts)
                            context.toast(context.getString(R.string.no_more_posts))
                        }
                    } else {
                        context.toast(context.getString(R.string.update_error))
                    }
                }
            }
        }
    }
}
