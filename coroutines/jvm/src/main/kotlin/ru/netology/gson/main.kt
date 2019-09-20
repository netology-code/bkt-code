package ru.netology.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.netology.gson.model.Post
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

fun main() {
    val list = listOf(
        Post(1, "Netology", "First post in our network!", 1566408240),
        Post(2, "Netology", created = 1566418240)
    )

    println(Gson().toJson(list))

    val gson = GsonBuilder().apply {
        setPrettyPrinting()
        serializeNulls()
    }.create()
    Files.write(Paths.get("./output.json"), gson.toJson(list).toByteArray(), StandardOpenOption.CREATE)
}