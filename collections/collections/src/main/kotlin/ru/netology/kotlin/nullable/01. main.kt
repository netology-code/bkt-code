package ru.netology.kotlin.nullable


fun main() {
    val original = Post(1, "Netology", "First post in our network!", 1566408240)
    val repost = Post(
        2,
        "Student",
        created = 1567022400,
        postType = PostType.REPOST,
        source = original
    )

//    println(repost.source.author)
//    println(repost.source!!.author)

    // safe call
    println(repost.source?.author?.toUpperCase())

    // smart cast
    if (repost.source != null) {
        // здесь можем обращаться к repost.source
        // т.к. Kotlin точно знает, что source != null
        println(repost.source.author)
    }

    // elvis
    val content = repost.content ?: "No content"
    println(content)
}