package ru.netology.kotlin.ncraftmedia.adapter.postfeed

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.netology.kotlin.ncraftmedia.R
import ru.netology.kotlin.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.post_feed_post_card.view.*

class PostViewHolder(adapter: PostAdapter, view: View): BaseViewHolder(adapter, view) {
    init {
        with(itemView) {
            likeBtn.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    item.likedByMe = !item.likedByMe
                    adapter.notifyItemChanged(adapterPosition)
                }
            }
            shareBtn.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val item = adapter.list[adapterPosition]
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT, """
                                ${item.author} (${item.created})
    
                                ${item.content}
                            """.trimIndent()
                        )
                        type = "text/plain"
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun bind(post: Post) {
        with(itemView) {
            authorTv.text = post.author
            contentTv.text = post.content

            if (post.likedByMe) {
                likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
                likesTv.setTextColor(0xFF0000)
            } else {
                likeBtn.setImageResource(R.drawable.ic_favorite_inactive_24dp)
                likesTv.setTextColor(0x999999)
            }
        }
    }
}
