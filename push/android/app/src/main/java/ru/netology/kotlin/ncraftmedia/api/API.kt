package ru.netology.kotlin.ncraftmedia.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class AuthRequestParams(val username: String, val password: String)

data class PushRequestParams(val token: String)

data class Token(val token: String)

interface API {
    @POST("api/v1/authentication")
    suspend fun authenticate(@Body authRequestParams: AuthRequestParams): Response<Token>

    @POST("api/v1/push")
    suspend fun registerPushToken(@Header("Authorization") token: String, @Body pushRequestParams: PushRequestParams): Response<Nothing>
}

object Repository {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.8.0.17:7777")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val API: API by lazy {
        retrofit.create(API::class.java)
    }

    private var token: String? = null

    suspend fun authenticate(login: String, password: String) {
        return API.authenticate(AuthRequestParams(login, password)).let {
            if (it.isSuccessful) {
                token = it.body()?.token
            }
        }
    }

    suspend fun registerPushToken(token: String) = API.registerPushToken(this.token!!, PushRequestParams(token))
}