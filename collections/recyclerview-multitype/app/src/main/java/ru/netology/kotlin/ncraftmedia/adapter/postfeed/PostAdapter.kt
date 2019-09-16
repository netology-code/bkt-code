package ru.netology.kotlin.ncraftmedia.adapter.postfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.kotlin.ncraftmedia.R
import ru.netology.kotlin.ncraftmedia.dto.Post
import ru.netology.kotlin.ncraftmedia.dto.PostType

const val VIEW_TYPE_POST = 1
const val VIEW_TYPE_REPOST = 2
const val VIEW_TYPE_REPLY = 3

fun viewTypeToPostType(viewType: Int) = when (viewType) {
    VIEW_TYPE_POST -> PostType.POST
    VIEW_TYPE_REPOST -> PostType.REPOST
    VIEW_TYPE_REPLY -> PostType.REPLY
    else -> TODO("unknown view type")
}

class PostAdapter(val list: List<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewTypeToPostType(viewType)) {
            PostType.POST -> PostViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_feed_post_card,
                    parent,
                    false
                )
            )
            PostType.REPOST -> RepostViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_feed_repost_card,
                    parent,
                    false
                )
            )
            PostType.REPLY -> ReplyViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.post_feed_reply_card,
                    parent,
                    false
                )
            )
        }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = list[position].id

    override fun getItemViewType(position: Int) = when (list[position].postType) {
        PostType.POST -> VIEW_TYPE_POST
        PostType.REPOST -> VIEW_TYPE_REPOST
        PostType.REPLY -> VIEW_TYPE_REPLY
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as BaseViewHolder) {
            bind(list[position])
        }
    }
}

