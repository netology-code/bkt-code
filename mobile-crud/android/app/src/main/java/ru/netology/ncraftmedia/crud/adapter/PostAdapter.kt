package ru.netology.ncraftmedia.crud.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import ru.netology.ncraftmedia.R
import ru.netology.ncraftmedia.crud.dto.PostModel
import splitties.activities.startActivity
import splitties.toast.toast

class PostAdapter(val list: List<PostModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  var likeBtnClickListener: OnLikeBtnClickListener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    return PostViewHolder(this, view)
  }

  override fun getItemCount() = list.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    Log.v("test", "position is $position")
    with(holder as PostViewHolder) {
      bind(list[position])
    }
  }

  interface OnLikeBtnClickListener {
    fun onLikeBtnClicked(item: PostModel, position: Int)
  }
}


class PostViewHolder(val adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {
  init {
    with(itemView) {
      likeBtn.setOnClickListener {
        val currentPosition = adapterPosition
        if (currentPosition != RecyclerView.NO_POSITION) {
          val item = adapter.list[currentPosition]
          if (item.likeActionPerforming) {
              context.toast(context.getString(R.string.like_in_progress))
          } else {
            adapter.likeBtnClickListener?.onLikeBtnClicked(item, currentPosition)
          }
        }
      }
      shareBtn.setOnClickListener {
        if (adapterPosition != RecyclerView.NO_POSITION) {
          val item = adapter.list[adapterPosition]
          itemView.context.startActivity(Intent.ACTION_SEND) {
            putExtra(
              Intent.EXTRA_TEXT, """
                                ${item.ownerName} (${item.created})
    
                                ${item.content}
                            """.trimIndent()
            )
            type = "text/plain"
          }
        }
      }
    }
  }

  fun bind(post: PostModel) {
    with(itemView) {
      authorTv.text = post.ownerName
      contentTv.text = post.content
      likesTv.text = post.likes.toString()

      when {
        post.likeActionPerforming -> likeBtn.setImageResource(R.drawable.ic_favorite_pending_24dp)
        post.likedByMe -> {
          likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
          likesTv.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
        }
        else -> {
          likeBtn.setImageResource(R.drawable.ic_favorite_inactive_24dp)
          likesTv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey))
        }
      }
    }
  }
}
