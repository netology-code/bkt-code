package ru.netology

import io.ktor.http.*
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {
    private val jsonContentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)

    @Test // аннотация
    fun testGetAll() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/v1/posts").run {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(jsonContentType, response.contentType())
            }
        }
    }

    @Test
    fun testAdd() {
        withTestApplication({ module() }) {
            with(handleRequest(HttpMethod.Post, "/api/v1/posts") {
                addHeader(HttpHeaders.ContentType, jsonContentType.toString())
                setBody(
                    """
                        {
                            "id": 0,
                            "author": "Vasya"
                        }
                    """.trimIndent()
                )
            }) {
                response
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content!!.contains("\"id\": 1"))
            }
        }
    }
}