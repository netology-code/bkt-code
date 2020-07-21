package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R
import ru.netology.ncraftmedia.crud.adapter.PostAdapter
import splitties.activities.start
import splitties.toast.toast
import java.io.IOException


class FeedActivity : AppCompatActivity() {

  private var dialog: ProgressDialog? = null
  private var adapter: PostAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_feed)
    // используется Splitties(Activity)
    fab.setOnClickListener { start<CreatePostActivity>() }
  }

  override fun onStart() {
    super.onStart()
    lifecycleScope.launch {
      dialog =
        indeterminateProgressDialog(
          message = R.string.please_wait,
          title = R.string.downloading_posts
        )
      try {
        val result = Repository.getRecent()
        if (result.isSuccessful) {
          adapter = PostAdapter(result.body() ?: mutableListOf())
          with(container) {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            this.adapter = this@FeedActivity.adapter
          }
        } else {
          toast(R.string.error_occured)
        }
      } catch (e: IOException) {
        toast("error occurred")
      }finally {
        dialog?.dismiss()
      }
    }
  }
}
