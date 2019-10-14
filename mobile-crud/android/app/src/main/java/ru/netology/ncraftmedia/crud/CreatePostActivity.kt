package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import ru.netology.ncraftmedia.R
import java.io.IOException

class CreatePostActivity : AppCompatActivity(), CoroutineScope by MainScope() {

  private var dialog: ProgressDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_create_post)

    createPostBtn.setOnClickListener {
      launch {
        // Показываем крутилку
        dialog =
          indeterminateProgressDialog(
            message = R.string.please_wait,
            title = R.string.create_new_post
          ) {
            setCancelable(false)
          }
        // Обворачиваем в try catch, потому что возможны ошибки при соединении с сетью
        try {
          val result = Repository.createPost(contentEdt.text.toString())
          if (result.isSuccessful) {
            // обрабатываем успешное создание поста
            handleSuccessfullResult()
          } else {
            // обрабоатываем ошибку
            handleFailedResult()
          }
        } catch (e: IOException) {
          // обрабоатываем ошибку
          handleFailedResult()
        } finally {
          // закрываем диалог
          dialog?.dismiss()
        }

      }
    }

  }

  private fun handleSuccessfullResult() {
    toast(R.string.post_created_successfully)
    finish()
  }

  private fun handleFailedResult() {
    toast(R.string.error_occured)
  }
}
