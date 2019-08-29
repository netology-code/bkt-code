package ru.netology.kotlin.ncraftmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
        }

        likeBtn.setOnClickListener {
            post.likedByMe = !post.likedByMe
            likeBtn.setImageResource(if (post.likedByMe) R.drawable.ic_favorite_active_24dp else R.drawable.ic_favorite_inactive_24dp)
        }

        shareBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, """
                    ${post.author} (${post.created})

                    ${post.content}
                """.trimIndent())
                type = "text/plain"
            }
            startActivity(intent)
        }
    }
}
