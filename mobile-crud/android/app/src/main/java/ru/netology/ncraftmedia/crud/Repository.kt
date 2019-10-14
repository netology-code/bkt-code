package ru.netology.ncraftmedia.crud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.ncraftmedia.crud.api.API
import ru.netology.ncraftmedia.crud.api.AuthRequestParams
import ru.netology.ncraftmedia.crud.api.CreatePostRequest
import ru.netology.ncraftmedia.crud.api.RegistrationRequestParams
import ru.netology.ncraftmedia.crud.api.interceptor.InjectAuthTokenInterceptor


object Repository {

  private var retrofit: Retrofit =
    Retrofit.Builder()
      .baseUrl("http://10.0.2.2:8888")
      .addConverterFactory(GsonConverterFactory.create())
      .build()


  // Добавление interceptor-ов в retrofit клиент. Во все последующие запросы будет добавляться токен
  // и они будут логироваться
  fun createRetrofitWithAuth(authToken: String) {
    val httpLoggerInterceptor = HttpLoggingInterceptor()
    // Указываем, что хотим логировать тело запроса.
    httpLoggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
      .addInterceptor(InjectAuthTokenInterceptor(authToken))
      .addInterceptor(httpLoggerInterceptor)
      .build()
    retrofit = Retrofit.Builder()
      .client(client)
      .baseUrl("http://10.0.2.2:8888")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    //создаем API на основе нового retrofit-клиента
    API = retrofit.create(ru.netology.ncraftmedia.crud.api.API::class.java)
  }

  // Ленивое создание API
  private var API: API =
    retrofit.create(ru.netology.ncraftmedia.crud.api.API::class.java)


  suspend fun authenticate(login: String, password: String) = API.authenticate(
    AuthRequestParams(login, password)
  )

  suspend fun register(login: String, password: String) =
    API.register(
      RegistrationRequestParams(
        login,
        password
      )
    )

  suspend fun createPost(content: String) = API.createPost(CreatePostRequest(content = content))

  suspend fun getPosts() = API.getPosts()

  suspend fun likedByMe(id: Long) = API.likedByMe(id)

  suspend fun cancelMyLike(id: Long) = API.cancelMyLike(id)
}
