package ru.netology.ncraftmedia.crud

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.core.content.edit
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
                toast("Password aren't the same")
            } else if (!isValid(password)) {
                toast("Password is incorrect")
            } else {
                lifecycleScope.launch {
                    dialog =
                        indeterminateProgressDialog(
                            message = R.string.please_wait,
                            title = R.string.authentication
                        )
                    val responce =
                        Repository.register(
                            edt_registration_login.text.toString(),
                            password
                        )
                    dialog?.dismiss()
                    if (responce.isSuccessful) {
                        toast(R.string.success)
                        setUserAuth(responce.body()!!.token)
                        finish()
                    } else {
                        toast(R.string.registration_failed)
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
