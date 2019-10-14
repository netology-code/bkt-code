package ru.netology.ncraftmedia.crud

import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.ncraftmedia.crud.api.*
import ru.netology.ncraftmedia.crud.api.interceptor.InjectAuthTokenInterceptor
import ru.netology.ncraftmedia.crud.dto.AttachmentModel
import java.io.ByteArrayOutputStream


object Repository {

  private var retrofit: Retrofit =
    Retrofit.Builder()
      .baseUrl(BASE_URL)
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
      .baseUrl(BASE_URL)
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

  suspend fun createPost(
    content: String,
    attachmentModel: AttachmentModel?
  ) = API.createPost(
    CreatePostRequest(
      content = content,
      attachmentId = attachmentModel?.id
    )
  )

  suspend fun getPosts() = API.getPosts()

  suspend fun getRecent() = API.getRecent()

  suspend fun likedByMe(id: Long) = API.likedByMe(id)

  suspend fun cancelMyLike(id: Long) = API.cancelMyLike(id)

  suspend fun makeRepost(sourceId: Long, content: String) =
    API.repost(sourceId, PostRequest(content = content))

  suspend fun getPostsAfter(id: Long) = API.after(id)

  suspend fun getPostsBefore(id: Long) = API.before(id)

  suspend fun upload(bitmap: Bitmap): Response<AttachmentModel> {
    TODO()
  }
}
