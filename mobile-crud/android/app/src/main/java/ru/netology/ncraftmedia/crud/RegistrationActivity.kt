package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R
import splitties.toast.toast

class RegistrationActivity : AppCompatActivity() {

  private var dialog: ProgressDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_registration)

    btn_register.setOnClickListener {
      val password = edt_registration_password.text.toString()
      val repeatedPassword = edt_registration_repeat_password.text.toString()
      if (password != repeatedPassword) {
        toast(R.string.password_arent_match)
      } else if (!isValid(password)) {
        toast(getString(R.string.password_incorrect))
      } else {
        lifecycleScope.launch {
          dialog = ProgressDialog(this@RegistrationActivity).apply {
            setMessage(this@RegistrationActivity.getString(R.string.please_wait))
            setTitle(R.string.authentication)
            setCancelable(false)
            setProgressBarIndeterminate(true)
            show()
          }
          val response = Repository.register(edt_registration_login.text.toString(), password)
          dialog?.dismiss()
          if (response.isSuccessful) {
            toast(getString(R.string.success))
            setUserAuth(response.body()!!.token)
            finish()
          } else {
            toast(getString(R.string.registration_failed))
          }
        }
      }
    }
  }

  private fun setUserAuth(token: String) =
    getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
      .edit()
      .putString(AUTHENTICATED_SHARED_KEY, token)
      .commit()
}
