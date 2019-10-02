package ru.netology.saturn33.homework.hw9.client

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.netology.saturn33.homework.hw9.dto.AuthenticationRequestDto
import ru.netology.saturn33.homework.hw9.dto.AuthenticationResponseDto

interface RAPI {
    @POST("authentication")
    suspend fun authenticate(@Body authRequestParams: AuthenticationRequestDto): Response<AuthenticationResponseDto>
}
