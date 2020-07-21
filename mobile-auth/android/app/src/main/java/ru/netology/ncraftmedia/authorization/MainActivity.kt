package ru.netology.ncraftmedia.authorization

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import ru.netology.ncraftmedia.R
import splitties.activities.start
import splitties.toast.toast

class MainActivity : AppCompatActivity() {

    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isAuthenticated()) {
            start<FeedActivity>()
            finish()
        } else {
            btn_login.setOnClickListener {
                if (!isValid(edt_password.text.toString())) {
                    edt_password.error = "Password is incorrect"
                } else {
                    lifecycleScope.launch {
                        dialog =
                            indeterminateProgressDialog(
                                message = R.string.please_wait,
                                title = R.string.authentication
                            )
                        val responce =
                            Repository.authenticate(
                                edt_login.text.toString(),
                                edt_password.text.toString()
                            )
                        dialog?.dismiss()
                        if (responce.isSuccessful) {
                            toast(R.string.success)
                            setUserAuth(responce.body()!!.token)
                            start<FeedActivity>()
                            finish()
                        } else {
                            toast(R.string.authentication_failed)
                        }
                    }
                }
            }
        }

        btn_registration.setOnClickListener {
            start<RegistrationActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        if (isAuthenticated()) {
            start<FeedActivity>()
            finish()
        }
    }

    private fun isAuthenticated() =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY, ""
        )?.isNotEmpty() ?: false

    private fun setUserAuth(token: String) = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, token)
            .commit()
}
