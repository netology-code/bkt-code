package ru.netology.ncraftmedia.crud.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R
import ru.netology.ncraftmedia.crud.Repository
import ru.netology.ncraftmedia.crud.dto.PostModel
import splitties.activities.startActivity
import splitties.toast.toast

class PostAdapter(val list: MutableList<PostModel>) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  fun newRecentPosts(posts: MutableList<PostModel>) {
    list.clear()
    list.addAll(posts)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val postView =
      LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
    return PostViewHolder(this, postView)
  }

  // items + 1 footer +1 header
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
        val currentPosition = adapterPosition
        if (currentPosition != RecyclerView.NO_POSITION) {
          val item = adapter.list[currentPosition]
          if (item.likeActionPerforming) {
            context.toast("Like is performing")
          } else {
            GlobalScope.launch(Dispatchers.Main) {
              item.likeActionPerforming = true
              adapter.notifyItemChanged(currentPosition)
              val response =
                Repository.likedByMe(item.id)
              item.likeActionPerforming = false
              if (response.isSuccessful) {
                item.updatePost(response.body()!!)
              }
              adapter.notifyItemChanged(currentPosition)
            }
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


      if (post.likeActionPerforming) {
        likeBtn.setImageResource(R.drawable.ic_favorite_pending_24dp)
      } else if (post.likedByMe) {
        likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
        likesTv.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
      } else {
        likeBtn.setImageResource(R.drawable.ic_favorite_inactive_24dp)
        likesTv.setTextColor(ContextCompat.getColor(context, R.color.colorBrown))
      }
    }
  }
}
