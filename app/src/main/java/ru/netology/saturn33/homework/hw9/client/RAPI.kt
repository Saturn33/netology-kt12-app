package ru.netology.saturn33.homework.hw9.client

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.netology.saturn33.homework.hw9.dto.*

interface RAPI {
    @POST("authentication")
    suspend fun authenticate(@Body authRequestParams: AuthenticationRequestDto): Response<AuthenticationResponseDto>

    @POST("registration")
    suspend fun register(@Body authRequestParams: RegistrationRequestDto): Response<RegistrationResponseDto>

    @GET("me")
    suspend fun me(): Response<UserResponseDto>
}
