package ru.netology.saturn33.homework.hw10.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_load_new.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.repositories.Repository

class HeaderViewHolder(val adapter: PostAdapter, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    init {
        with(itemView) {
            btnLoadNew.setOnClickListener {
                btnLoadNew.isEnabled = false
                progressBarNew.isVisible = true
                GlobalScope.launch(Dispatchers.Main) {
                    val response =
                        Repository.getPostsAfter(if (adapter.list.isEmpty()) 0 else adapter.list.first().id)
                    progressBarNew.isVisible = false
                    btnLoadNew.isEnabled = true

                    if (response.isSuccessful) {
                        val newItems = response.body()!!
                        if (newItems.size > 0) {
                            adapter.list.addAll(0, newItems)
                            adapter.notifyItemRangeInserted(0, newItems.size)
                        }
                    } else {
                        context.toast(context.getString(R.string.update_error))
                    }
                }
            }
        }
    }
}
