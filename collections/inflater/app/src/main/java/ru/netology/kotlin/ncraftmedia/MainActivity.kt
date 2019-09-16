package ru.netology.kotlin.ncraftmedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_feed_post_card.view.*
import ru.netology.kotlin.ncraftmedia.dto.Post

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(
            Post(4, "Netology", "Kotlin MeetUp!", 1566818240, likedByMe = true),
            Post(3, "Netology", "Welcome to Kotlin Course!", 1566518240),
            Post(2, "Netology", "Our network is growing!", 1566418240, likedByMe = true),
            Post(1, "Netology", "First it in our network!", 1566408240)
        )

        list.forEach { post ->
            val view = layoutInflater.inflate(R.layout.post_feed_post_card, container, false).apply {
                authorTv.text = post.author
                contentTv.text = post.content

                if (post.likedByMe) {
                    likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
                }

                likeBtn.setOnClickListener {
                    post.likedByMe = !post.likedByMe
                    likesTv.setTextColor(0xFF0000)
                    likeBtn.setImageResource(if (post.likedByMe) R.drawable.ic_favorite_active_24dp else R.drawable.ic_favorite_inactive_24dp)
                }

                shareBtn.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT, """
                    ${post.author} (${post.created})

                    ${post.content}
                """.trimIndent()
                        )
                        type = "text/plain"
                    }
                    startActivity(intent)
                }
            }
            container.addView(view)
        }
    }
}
