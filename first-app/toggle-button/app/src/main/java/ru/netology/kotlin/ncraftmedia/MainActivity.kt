package ru.netology.kotlin.ncraftmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.kotlin.ncraftmedia.dto.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post = Post(1, "Vasya", "First post in our network!", "20 august 2019")
        createdTv.text = post.created
        contentTv.text = post.content

        if (post.likedByMe) {
            likeBtn.isChecked = true
        }
    }
}
