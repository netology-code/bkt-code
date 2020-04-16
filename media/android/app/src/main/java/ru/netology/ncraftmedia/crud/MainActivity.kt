package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R
import splitties.activities.start
import splitties.toast.toast

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

  private var dialog: ProgressDialog? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (/*isAuthenticated()*/true) {
      val token = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
        AUTHENTICATED_SHARED_KEY, "blabla"
      )
      Repository.createRetrofitWithAuth(token!!)
      val feedActivityIntent = Intent(this@MainActivity, FeedActivity::class.java)
      startActivity(feedActivityIntent)
      finish()
    } else {
      btn_login.setOnClickListener {
        if (!isValid(edt_password.text.toString())) {
          edt_password.error = "Password is incorrect"
        } else {
          launch {
            dialog =
              indeterminateProgressDialog(
                message = R.string.please_wait,
                title = R.string.authentication
              ) {
                setCancelable(false)
              }
            val responce =
              Repository.authenticate(
                edt_login.text.toString(),
                edt_password.text.toString()
              )
            dialog?.dismiss()
            if (responce.isSuccessful) {
              toast(R.string.success)
              setUserAuth(responce.body()!!.token)
              Repository.createRetrofitWithAuth(responce.body()!!.token)
              val feedActivityIntent =
                Intent(this@MainActivity, FeedActivity::class.java)
              startActivity(feedActivityIntent)
              finish()
            } else {
              toast(R.string.authentication_failed)
            }
          }
        }
      }
    }

    btn_registration.setOnClickListener {
      val registrationIntent = Intent(this@MainActivity, RegistrationActivity::class.java)
      startActivity(registrationIntent)
    }
  }

  override fun onStart() {
    super.onStart()
    if (isAuthenticated()) {
      val token = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
        AUTHENTICATED_SHARED_KEY, ""
      )
      Repository.createRetrofitWithAuth(token!!)
      start<FeedActivity>()
      finish()
    }
  }

  private fun isAuthenticated() =
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
      AUTHENTICATED_SHARED_KEY, ""
    )?.isNotEmpty() ?: false

  private fun setUserAuth(token: String) =
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit {
        putString(AUTHENTICATED_SHARED_KEY, token)
    }

  override fun onStop() {
    super.onStop()
    cancel()
    dialog?.dismiss()
  }
}
