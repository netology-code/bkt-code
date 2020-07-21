package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R
import splitties.toast.toast
import java.io.IOException

class CreatePostActivity : AppCompatActivity() {

  private var dialog: ProgressDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_create_post)

    createPostBtn.setOnClickListener {
      lifecycleScope.launch {
        // Показываем крутилку
        dialog = ProgressDialog(this@CreatePostActivity).apply {
          setMessage(this@CreatePostActivity.getString(R.string.please_wait))
          setTitle(R.string.create_new_post)
          setCancelable(false)
          setProgressBarIndeterminate(true)
          show()
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
          // обрабатываем ошибку
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
