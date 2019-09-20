package ru.netology.kotlin.ncraftmedia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.request.get
import kotlinx.coroutines.*
import ru.netology.kotlin.ncraftmedia.client.Api
import ru.netology.kotlin.ncraftmedia.dto.Post
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()
    }

    fun fetchData() = launch {
        val list = withContext(Dispatchers.IO) {
            Api.client.get<List<Post>>(Api.url)
        }
        Toast.makeText(this@MainActivity, "Length: ${list.size}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
