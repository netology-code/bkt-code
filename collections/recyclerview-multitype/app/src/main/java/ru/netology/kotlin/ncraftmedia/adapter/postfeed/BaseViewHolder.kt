package ru.netology.kotlin.ncraftmedia.adapter.postfeed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.netology.kotlin.ncraftmedia.dto.Post

abstract class BaseViewHolder(val adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(post: Post)
}