package ru.netology.saturn33.homework.hw12.repositories

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.saturn33.homework.hw12.API_BASE_URL
import ru.netology.saturn33.homework.hw12.PAGE_SIZE
import ru.netology.saturn33.homework.hw12.client.InjectAuthTokenInterceptor
import ru.netology.saturn33.homework.hw12.client.RAPI
import ru.netology.saturn33.homework.hw12.dto.*
import java.io.ByteArrayOutputStream

object Repository {
    private var retrofit: Retrofit = createRetrofit(returnInstance = true)
    //        Retrofit.Builder()
//            .baseUrl(API_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
    private var API: RAPI = retrofit.create(RAPI::class.java)

    fun createRetrofit(authToken: String? = null, returnInstance: Boolean = false): Retrofit {
        val httpLoggerInterceptor = HttpLoggingInterceptor()
        httpLoggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val clientBuilder = OkHttpClient.Builder()
        if (authToken != null)
            clientBuilder.addInterceptor(InjectAuthTokenInterceptor(authToken))
        clientBuilder.addInterceptor(httpLoggerInterceptor)
        val client = clientBuilder.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        if (returnInstance)
            return retrofit
        API = retrofit.create(RAPI::class.java)

        return retrofit
    }

    suspend fun authenticate(login: String, password: String) = API.authenticate(
        AuthenticationRequestDto(login, password)
    )

    suspend fun register(login: String, password: String) = API.register(
        RegistrationRequestDto(login, password)
    )

    suspend fun createPost(content: String?, attachment: AttachmentModel?) = API.createPost(
        PostRequestDto(content = content, attachment = attachment)
    )

    suspend fun createRepost(postId: Long, content: String?) = API.createRepost(
        postId,
        PostRequestDto(content = content)
    )

    suspend fun getPosts() = API.getPosts()

    suspend fun getRecentPosts() = API.getRecentPosts(PAGE_SIZE)

    suspend fun getPostsAfter(id: Long) = API.getPostsAfter(id)

    suspend fun getPostsBefore(id: Long) = API.getPostsBefore(id, PAGE_SIZE)

    suspend fun like(id: Long) = API.like(id)

    suspend fun dislike(id: Long) = API.dislike(id)

    suspend fun registerPushToken(token: String) = API.registerPushToken(PushRequestParamsDto(token))

    suspend fun me() = API.me()

    suspend fun upload(bitmap: Bitmap): Response<AttachmentModel> {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val reqFile = bos.toByteArray().toRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", "image.jpg", reqFile)
        return API.uploadImage(body)
    }
}
