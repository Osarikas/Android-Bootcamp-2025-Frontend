package ru.sicampus.bootcamp2025.data.login

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sicampus.bootcamp2025.data.UserDTO
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class LoginNetworkDataSource {
    private val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    @OptIn(ExperimentalEncodingApi::class)
    suspend fun login(email: String, password: String):Result<Unit> = withContext(Dispatchers.IO){
        runCatching {
            val result = client.post("http://45.134.12.60:8080/api/volunteers/login"){
                headers{
                    basicAuth(email, password)
                }
            }

            if (result.status != HttpStatusCode.OK) {
                error("Status ${result.status}")
            }
            result.body()
        }
    }
}