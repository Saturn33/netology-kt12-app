package ru.netology.saturn33.homework.hw12.client

import okhttp3.Interceptor
import okhttp3.Response
import ru.netology.saturn33.homework.hw12.AUTH_TOKEN_HEADER

class InjectAuthTokenInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithToken = originalRequest.newBuilder()
            .header(AUTH_TOKEN_HEADER, "Bearer $authToken")
            .build()
        return chain.proceed(requestWithToken)
    }
}