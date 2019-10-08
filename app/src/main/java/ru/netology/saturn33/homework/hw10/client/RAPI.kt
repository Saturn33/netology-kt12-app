package ru.netology.saturn33.homework.hw10.client

import retrofit2.Response
import retrofit2.http.*
import ru.netology.saturn33.homework.hw10.dto.*

interface RAPI {
    @POST("authentication")
    suspend fun authenticate(@Body authRequestParams: AuthenticationRequestDto): Response<AuthenticationResponseDto>

    @POST("registration")
    suspend fun register(@Body authRequestParams: RegistrationRequestDto): Response<RegistrationResponseDto>

    @POST("posts")
    suspend fun createPost(@Body createPostRequest: PostRequestDto): Response<Void>

    @GET("posts")
    suspend fun getPosts(): Response<MutableList<PostModel>>

    @POST("posts/{id}/like")
    suspend fun like(@Path("id") id: Long): Response<PostModel>

    @DELETE("posts/{id}/like")
    suspend fun dislike(@Path("id") id: Long): Response<PostModel>

    @POST("posts/{id}/repost")
    suspend fun createRepost(@Path("id") postId: Long, @Body createPostRequest: PostRequestDto): Response<Void>


    @GET("me")
    suspend fun me(): Response<UserResponseDto>
}
