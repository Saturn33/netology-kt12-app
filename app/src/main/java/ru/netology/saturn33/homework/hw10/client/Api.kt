package ru.netology.saturn33.homework.hw10.client

import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.util.KtorExperimentalAPI

object Api {
    const val urlBasic = "https://raw.githubusercontent.com/Saturn33/netology-kt-6-back/master/postsBasic.json"
    const val urlAdv = "https://raw.githubusercontent.com/Saturn33/netology-kt-6-back/master/postsAdv.json"

    @KtorExperimentalAPI
    val client = HttpClient {
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }
}