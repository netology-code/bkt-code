package ru.netology.ktor

import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.coroutines.*
import ru.netology.ktor.model.Post
import ru.netology.ktor.model.PostType
import kotlin.coroutines.EmptyCoroutineContext

const val url = "https://raw.githubusercontent.com/netology-code/bkt-code/master/coroutines/posts.json"
//const val url = "http://127.0.0.1:38207/output.json"

/*
fun main() {
    val client = HttpClient {
        // DSL -> Feature
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }

    val response = client.get<List<Post>>(url) // GET-запрос
    println(response)
    client.close()
}
*/

/*
fun main() {
    val client = HttpClient {
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }

    with(CoroutineScope(EmptyCoroutineContext)) {
        // this -> CoroutineScope
        println("before launch: ${Thread.currentThread().name}")
        launch(Dispatchers.Unconfined) {
            println("in launch: ${Thread.currentThread().name}")
            val response = client.get<List<Post>>(url)
            println("job: $response")
            println("in launch: ${Thread.currentThread().name}")
        }
    }

    Thread.sleep(5000) // blocking call
    println(Thread.currentThread().name)
}
*/

/*
fun main() {
    val client = HttpClient {
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }

    with(CoroutineScope(EmptyCoroutineContext)) {
        println("before launch: ${Thread.currentThread().name}")
        repeat(20) {
            launch(Dispatchers.Main) {
                println("start launch $it: ${Thread.currentThread().name}")
                val response = client.get<List<Post>>(url)
                println("launch $it: $response")
                println("finish launch $it: ${Thread.currentThread().name}")
            }
        }
    }

    Thread.sleep(5000)
    println(Thread.currentThread().name)
}
*/

/*
fun main() {
    val client = HttpClient {
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.INFO
        }
    }

    with(CoroutineScope(EmptyCoroutineContext)) {
        println("before launch: ${Thread.currentThread().name}")
        launch {
            println("start launch job1: ${Thread.currentThread().name}")
            val response = client.get<List<Post>>(url)
            println("job job1: $response")
            println("finish launch job1: ${Thread.currentThread().name}")
        }
        launch {
            println("start launch job2: ${Thread.currentThread().name}")
            val response = client.get<List<Post>>(url)
            println("job job2: $response")
            println("finish launch job2: ${Thread.currentThread().name}")
        }
//        cancel()
    }

    Thread.sleep(5000)
    println(Thread.currentThread().name)
}
*/

fun main() {
    val client = HttpClient {
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.HEADERS
        }
    }

    with(CoroutineScope(EmptyCoroutineContext)) {
        println("before launch: ${Thread.currentThread().name}")
        launch {
            println("start launch job1: ${Thread.currentThread().name}")
            val response = client.get<List<Post>>(url)
            println("job job1: $response")
            println("finish launch job1: ${Thread.currentThread().name}")
        }
        launch {
            delay(1000)
            println("start launch job2: ${Thread.currentThread().name}")
            val response = client.get<List<Post>>(url)
            println("job job2: $response")
            println("finish launch job2: ${Thread.currentThread().name}")
        }
    }

    Thread.sleep(5000)
    println(Thread.currentThread().name)
}

/*
fun main() {
    val client = HttpClient {
        install(JsonFeature) {
//            acceptContentTypes = listOf(
//                ContentType.Text.Plain,
//                ContentType.Application.Json
//            )
            serializer = GsonSerializer()
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.HEADERS
        }
    }

    with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
        println("before launch: ${Thread.currentThread().name}")
        launch {
            println("start launch job1: ${Thread.currentThread().name}")
            val response = client.get<List<Post>>(url)
            println("job job1: $response")
            println("finish launch job1: ${Thread.currentThread().name}")
        }
        launch {
            delay(1000)
            println("start launch job2: ${Thread.currentThread().name}")
            val response = client.get<List<Post>>(url)
            println("job job2: $response")
            println("finish launch job2: ${Thread.currentThread().name}")
        }
    }

    Thread.sleep(5000)
    println(Thread.currentThread().name)
}
*/

