package ru.netology.saturn33.homework.hw10.adapter
/*

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.footer.view.*
import kotlinx.android.synthetic.main.post_event.view.*
import ru.netology.saturn33.homework.hw10.dto.PostModel

class EventViewHolder(adapter: PostAdapter, itemView: View) : PostViewHolder(adapter, itemView) {
    init {
        with(itemView) {
            imgLocation.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    item.location?.let { loc ->
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse("geo:${loc.lat},${loc.lng}?z=${loc.zoom}")
                        }.also {
                            itemView.context.startActivity(it)
                        }
                    }
                }
            }
        }
    }

    override fun bind(post: PostModel) {
        super.bind(post)
        with(itemView) {
            imgLocation.visibility = View.VISIBLE
            post.location?.address?.let {
                address.text = it
            }
        }
    }
}
*/
