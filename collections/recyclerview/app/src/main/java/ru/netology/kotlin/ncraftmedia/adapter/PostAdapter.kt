package ru.netology.kotlin.ncraftmedia.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_feed_post_card.view.*
import ru.netology.kotlin.ncraftmedia.R
import ru.netology.kotlin.ncraftmedia.dto.Post

class PostAdapter(val list: List<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_feed_post_card, parent, false)
        return PostViewHolder(this, view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as PostViewHolder) {
            bind(list[position])
        }
    }
}

class PostViewHolder(val adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {
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

    fun bind(post: Post) {
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
