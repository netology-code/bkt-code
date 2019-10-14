package ru.netology.ncraftmedia.crud.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import ru.netology.ncraftmedia.R
import ru.netology.ncraftmedia.crud.Repository
import ru.netology.ncraftmedia.crud.dto.PostModel

class PostAdapter(val list: List<PostModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
}

class PostViewHolder(val adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {
  init {
    with(itemView) {
      likeBtn.setOnClickListener {
        val currentPosition = adapterPosition
        if (currentPosition != RecyclerView.NO_POSITION) {
          val item = adapter.list[currentPosition]
          if (item.likeActionPerforming) {
            context.toast("Like is performing")
          } else {
            GlobalScope.launch(Dispatchers.Main) {
              item.likeActionPerforming = true
              adapter.notifyItemChanged(currentPosition)
              val response = if (item.likedByMe) {
                Repository.cancelMyLike(item.id)
              } else {
                Repository.likedByMe(item.id)
              }
              item.likeActionPerforming = false
              if (response.isSuccessful) {
                item.updateLikes(response.body()!!)
              }
              adapter.notifyItemChanged(currentPosition)
            }
          }
        }
      }
      shareBtn.setOnClickListener {
        if (adapterPosition != RecyclerView.NO_POSITION) {
          val item = adapter.list[adapterPosition]
          val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
              Intent.EXTRA_TEXT, """
                                ${item.ownerName} (${item.created})
    
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

  fun bind(post: PostModel) {
    with(itemView) {
      authorTv.text = post.ownerName
      contentTv.text = post.content
      likesTv.text = post.likes.toString()

      if (post.likeActionPerforming) {
        likeBtn.setImageResource(R.drawable.ic_favorite_pending_24dp)
      } else if (post.likedByMe) {
        likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
        likesTv.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
      } else {
        likeBtn.setImageResource(R.drawable.ic_favorite_inactive_24dp)
        likesTv.setTextColor(ContextCompat.getColor(context, R.color.colorGrey))
      }
    }
  }
}
