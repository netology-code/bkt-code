package ru.netology.ncraftmedia.crud.dialog

import android.app.AlertDialog
import android.content.Context
import kotlinx.android.synthetic.main.create_post.*
import ru.netology.ncraftmedia.R

fun showDialog(context: Context, createBtnClicked: (content: String) -> Unit) {
  val dialog = AlertDialog.Builder(context)
    .setView(R.layout.create_post)
    .show()
  dialog.createPostBtn.setOnClickListener {
    createBtnClicked(dialog.contentEdt.text.toString())
    dialog.dismiss()
  }
}