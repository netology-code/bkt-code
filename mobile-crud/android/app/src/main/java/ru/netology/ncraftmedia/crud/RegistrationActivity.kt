package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R

class RegistrationActivity : AppCompatActivity() {

  private var dialog: ProgressDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_registration)

    btn_register.setOnClickListener {
      val password = edt_registration_password.text.toString()
      val repeatedPassword = edt_registration_repeat_password.text.toString()
      if (password != repeatedPassword) {
        Toast.makeText(this, getString(R.string.password_arent_match), Toast.LENGTH_SHORT)
      } else if (!isValid(password)) {
        Toast.makeText(this, getString(R.string.password_incorrect), Toast.LENGTH_SHORT)
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
            Toast.makeText(
              this@RegistrationActivity,
              getString(R.string.success),
              Toast.LENGTH_SHORT
            )
            setUserAuth(response.body()!!.token)
            finish()
          } else {
            Toast.makeText(
              this@RegistrationActivity,
              getString(R.string.registration_failed),
              Toast.LENGTH_SHORT
            )
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
