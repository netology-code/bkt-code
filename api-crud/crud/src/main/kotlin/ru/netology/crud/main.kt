package ru.netology.crud

import com.google.gson.Gson
import kotlinx.coroutines.*
import ru.netology.crud.model.PostModel
import ru.netology.crud.repository.*
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.coroutines.EmptyCoroutineContext

/*
fun main() {
    var nextId = 1L
    with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
        repeat(10_000) {
            launch {
                nextId++
            }
        }
    }
    Thread.sleep(2000)
    println(nextId)
}
*/

/*
fun main() {
    val repo: PostRepository = PostRepositoryInMemoryConcurrentImpl()
    with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
        repeat(10_000) {
            launch {
                repo.save(PostModel(0, "Vasya"))
            }
        }
    }
    Thread.sleep(1000)
    with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
        launch {
            withContext(Dispatchers.IO) {
                Files.write(Paths.get("output.json"), Gson().toJson(repo.getAll()).toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
            }
            println(repo.getAll().size)
        }
    }
    Thread.sleep(1000)
}
*/

fun main() {
    repeat(50) {
        val repo: PostRepository = PostRepositoryInMemorySingleThreadedImpl()
        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            repeat(10_000) {
                launch {
                    repo.save(PostModel(0, "Vasya"))
                }
            }
        }

        Thread.sleep(1000)

        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            repeat(100_000) {
                launch {
                    repo.likeById(1L)
                }
            }
        }

        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            launch {
                println(repo.getById(1L))
                repo.removeById(1L)
            }
        }


        Thread.sleep(1000)

        with(CoroutineScope(EmptyCoroutineContext + SupervisorJob())) {
            launch {
                withContext(Dispatchers.IO) {
                    Files.write(
                        Paths.get("output.json"),
                        Gson().toJson(repo.getAll()).toByteArray(),
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
                    )
                }
                println("After remove: " + repo.getById(1L))
            }
        }
        Thread.sleep(1000)
    }
}
