package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R
import ru.netology.ncraftmedia.crud.dto.AttachmentModel
import ru.netology.ncraftmedia.crud.dto.AttachmentType
import splitties.toast.toast
import java.io.IOException

class CreatePostActivity : AppCompatActivity(), CoroutineScope by MainScope() {

  val REQUEST_IMAGE_CAPTURE = 1
  private var dialog: ProgressDialog? = null
  private var attachmentModel: AttachmentModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.create_post)


    createPostBtn.setOnClickListener {
      launch {
        // Показываем крутилку
        dialog =
          createProgressDialog()
        // Обворачиваем в try catch, потому что возможны ошибки при соединении с сетью
        try {
          val result = Repository.createPost(contentEdt.text.toString(), attachmentModel)
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

  private fun createProgressDialog(): ProgressDialog {
    return indeterminateProgressDialog(
      message = R.string.please_wait,
      title = R.string.create_new_post
    ) {
      setCancelable(false)
    }
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
      val imageBitmap = data?.extras?.get("data") as Bitmap?
      imageBitmap?.let {
        launch {
          dialog = createProgressDialog()
          val imageUploadResult = Repository.upload(it)
          NotifictionHelper.mediaUploaded(AttachmentType.IMAGE, this@CreatePostActivity)
          dialog?.dismiss()
          if (imageUploadResult.isSuccessful) {
            imageUploaded()
            attachmentModel = imageUploadResult.body()
          } else {
            toast("Can't upload image")
          }
        }
      }
    }
  }

  private fun imageUploaded() {
    transparetAllIcons()
    attachPhotoDoneImg.visibility = View.VISIBLE
  }

  private fun transparetAllIcons() {
    attachPhotoImg.setImageResource(R.drawable.ic_add_a_photo_inactive)
  }

  private fun dispatchTakePictureIntent() {
    TODO()
  }

  private fun handleSuccessfullResult() {
    toast(R.string.post_created_successfully)
    finish()
  }

  private fun handleFailedResult() {
    toast(R.string.error_occured)
  }
}
