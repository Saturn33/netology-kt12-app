package ru.netology.saturn33.homework.hw10.repositories

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.saturn33.homework.hw10.API_BASE_URL
import ru.netology.saturn33.homework.hw10.client.InjectAuthTokenInterceptor
import ru.netology.saturn33.homework.hw10.client.RAPI
import ru.netology.saturn33.homework.hw10.dto.AuthenticationRequestDto
import ru.netology.saturn33.homework.hw10.dto.PostRequestDto
import ru.netology.saturn33.homework.hw10.dto.RegistrationRequestDto

object Repository {
    private var retrofit: Retrofit = createRetrofit(returnInstance = true)
//        Retrofit.Builder()
//            .baseUrl(API_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
    private var API: RAPI = retrofit.create(RAPI::class.java)

    fun createRetrofit(authToken: String? = null, returnInstance: Boolean = false) : Retrofit {
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

    suspend fun createPost(content: String?) = API.createPost(
        PostRequestDto(content = content)
    )

    suspend fun createRepost(postId: Long, content: String?) = API.createRepost(
        postId,
        PostRequestDto(content = content)
    )

    suspend fun getPosts() = API.getPosts()

    suspend fun like(id: Long) = API.like(id)

    suspend fun dislike(id: Long) = API.dislike(id)

    suspend fun me() = API.me()
}
