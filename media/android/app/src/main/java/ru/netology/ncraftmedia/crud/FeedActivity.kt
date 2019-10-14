package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.ncraftmedia.R
import ru.netology.ncraftmedia.crud.adapter.PostAdapter
import ru.netology.ncraftmedia.crud.shcedule.UserNotHereWorker
import java.io.IOException
import java.util.concurrent.TimeUnit


class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {

  private var dialog: ProgressDialog? = null
  private var adapter: PostAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_feed)
    // используется Anko Commons(Intents)
    fab.setOnClickListener { startActivity<CreatePostActivity>() }
  }

  override fun onStart() {
    super.onStart()
    launch {
      dialog =
        indeterminateProgressDialog(
          message = R.string.please_wait,
          title = R.string.downloading_posts
        ) {
          setCancelable(false)
        }
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
