package ru.netology.kotlin.ncraftmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.kotlin.ncraftmedia.adapter.postfeed.PostAdapter
import ru.netology.kotlin.ncraftmedia.dto.Post
import ru.netology.kotlin.ncraftmedia.dto.PostType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val meetup = Post(4, "Netology", "Kotlin MeetUp!", 1566818240, likedByMe = true)

        val list = listOf(
            Post(6, "Netology", "Kotlin MeetUp!", 1566848240, likedByMe = true, postType = PostType.REPLY, source = meetup),
            Post(5, "Netology", "Kotlin MeetUp!", 1566838240, likedByMe = true, postType = PostType.REPOST, source = meetup),
            meetup,
            Post(3, "Netology", "Welcome to Kotlin Course!", 1566518240),
            Post(2, "Netology", "Our network is growing!", 1566418240, likedByMe = true),
            Post(1, "Netology", "First it in our network!", 1566408240)
        )

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(list)
        }
    }
}
