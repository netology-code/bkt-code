package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.ncraftmedia.R
import ru.netology.ncraftmedia.crud.adapter.PostAdapter
import ru.netology.ncraftmedia.crud.dto.PostModel

class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {

  private var dialog: ProgressDialog? = null

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
      val result = Repository.getPosts()
      dialog?.dismiss()
      if (result.isSuccessful) {
        with(container) {
          layoutManager = LinearLayoutManager(this@FeedActivity)
          adapter = PostAdapter(result.body() ?: emptyList<PostModel>())
        }
      } else {
        toast(R.string.error_occured)
      }
    }
  }
}
