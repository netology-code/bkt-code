package ru.netology.ncraftmedia.authorization

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import ru.netology.ncraftmedia.R

class MainActivity : AppCompatActivity() {

    private var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isAuthenticated()) {
            val feedActivityIntent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(feedActivityIntent)
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
                            Toast.makeText(this@MainActivity, R.string.success, Toast.LENGTH_SHORT)
                                .show()
                            setUserAuth(responce.body()!!.token)
                            val feedActivityIntent =
                                Intent(this@MainActivity, FeedActivity::class.java)
                            startActivity(feedActivityIntent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                R.string.authentication_failed,
                                Toast.LENGTH_SHORT
                            ).show()
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
            startActivity<FeedActivity>()
            finish()
        }
    }

    private fun isAuthenticated() =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY, ""
        )?.isNotEmpty() ?: false

    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(AUTHENTICATED_SHARED_KEY, token)
            .commit()
}
