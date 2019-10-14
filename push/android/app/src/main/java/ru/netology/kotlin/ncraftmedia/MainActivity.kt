package ru.netology.kotlin.ncraftmedia

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.design.longSnackbar
import ru.netology.kotlin.ncraftmedia.api.Repository
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestToken()
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

    private fun requestToken() {
        with(GoogleApiAvailability.getInstance()) {
            val code = isGooglePlayServicesAvailable(this@MainActivity)
            if (code == ConnectionResult.SUCCESS) {
                return@with
            }

            if (isUserResolvableError(code)) {
                getErrorDialog(this@MainActivity, code, 9000).show()
                return
            }

            root.longSnackbar(getString(R.string.google_play_unavailable))
            return
        }

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            launch {
                println(it.token)
//                Repository.registerPushToken(it.token)
            }
        }
    }
}
