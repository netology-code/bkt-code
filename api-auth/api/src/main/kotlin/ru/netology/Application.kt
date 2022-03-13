package ru.netology

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.server.cio.EngineMain
import kotlinx.coroutines.runBlocking
import org.kodein.di.bindEagerSingleton
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.kodein.di.with
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.netology.exception.ConfigurationException
import ru.netology.repository.PostRepository
import ru.netology.repository.PostRepositoryInMemoryWithMutexImpl
import ru.netology.repository.UserRepository
import ru.netology.repository.UserRepositoryInMemoryWithMutexImpl
import ru.netology.route.RoutingV1
import ru.netology.service.FileService
import ru.netology.service.JWTTokenService
import ru.netology.service.PostService
import ru.netology.service.UserService

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }

    install(StatusPages) {
        exception<NotImplementedError> {
            call.respond(HttpStatusCode.NotImplemented)
        }
        exception<ParameterConversionException> {
            call.respond(HttpStatusCode.BadRequest)
        }
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    di {
        constant(tag = "upload-dir") with (environment.config.propertyOrNull("ncraft.upload.dir")
            ?.getString() ?: throw ConfigurationException("Upload dir is not specified"))
        bindEagerSingleton<PasswordEncoder> { BCryptPasswordEncoder() }
        bindEagerSingleton { JWTTokenService() }
        bindEagerSingleton<PostRepository> { PostRepositoryInMemoryWithMutexImpl() }
        bindEagerSingleton { PostService(instance()) }
        bindEagerSingleton { FileService(instance(tag = "upload-dir")) }
        bindEagerSingleton<UserRepository> { UserRepositoryInMemoryWithMutexImpl() }
        bindEagerSingleton {
            UserService(instance(), instance(), instance()).apply {
                runBlocking {
                    this@apply.save("vasya", "password")
                }
            }
        }
        bindEagerSingleton {
            RoutingV1(
                staticPath = instance(tag = "upload-dir"),
                postService = instance(),
                fileService = instance(),
                userService = instance()
            )
        }
    }

    install(Authentication) {
        jwt {
            val jwtService by closestDI().instance<JWTTokenService>()
            verifier(jwtService.verifier)
            val userService by closestDI().instance<UserService>()

            validate {
                val id = it.payload.getClaim("id").asLong()
                userService.getModelById(id)
            }
        }
    }

    install(Routing) {
        val routingV1 by closestDI().instance<RoutingV1>()
        routingV1.setup(this)
    }
}