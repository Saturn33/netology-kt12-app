package ru.netology.saturn33.homework.hw12.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.netology.saturn33.homework.hw12.R
import ru.netology.saturn33.homework.hw12.dto.PostModel

abstract class BaseViewHolder(val adapter: PostAdapter, itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(post: PostModel)
    protected fun updateInfo(
        imgButton: ImageButton,
        txtView: TextView,
        active: Boolean,
        count: Int,
        activeDrawable: Int,
        inactiveDrawable: Int,
        pending: Boolean,
        pendingDrawable: Int,
        activeTextColor: Int = R.color.colorDigitsActive,
        inactiveTextColor: Int = R.color.colorDigits
    ) {
        if (pending) {
            imgButton.setImageResource(pendingDrawable)
            txtView.visibility = View.GONE
        }
        else
        {
            if (active) {
                imgButton.setImageResource(activeDrawable)
                txtView.setTextColor(itemView.context.resources.getColor(activeTextColor, null))
            } else {
                imgButton.setImageResource(inactiveDrawable)
                txtView.setTextColor(itemView.context.resources.getColor(inactiveTextColor, null))
            }
            if (count > 0) {
                txtView.text = count.toString()
                txtView.visibility = View.VISIBLE
            } else {
                txtView.visibility = View.GONE
            }
        }
    }
}