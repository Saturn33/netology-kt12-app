package ru.netology.saturn33.homework.hw10.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.netology.saturn33.homework.hw10.API_BASE_URL
import ru.netology.saturn33.homework.hw10.client.RAPI
import ru.netology.saturn33.homework.hw10.dto.AuthenticationRequestDto
import ru.netology.saturn33.homework.hw10.dto.RegistrationRequestDto

object Repository {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val API: RAPI by lazy {
        retrofit.create(RAPI::class.java)
    }

    suspend fun authenticate(login: String, password: String) = API.authenticate(
        AuthenticationRequestDto(login, password)
    )

    suspend fun register(login: String, password: String) = API.register(
        RegistrationRequestDto(login, password)
    )

    suspend fun me() = API.me()
}
