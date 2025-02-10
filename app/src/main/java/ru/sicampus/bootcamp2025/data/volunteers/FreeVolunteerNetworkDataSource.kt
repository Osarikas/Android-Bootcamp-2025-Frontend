package ru.sicampus.bootcamp2025.data.volunteers

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sicampus.bootcamp2025.data.ApiConfig
import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import kotlin.io.encoding.ExperimentalEncodingApi

class FreeVolunteerNetworkDataSource(
    context: Context
) {
    private val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val userDataStoreManager = UserDataStoreManager.getInstance(context)

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun getFreeVolunteers():Result<List<UserDTO>> = withContext(Dispatchers.IO){
        runCatching {
            val email = userDataStoreManager.emailFlow.first()
            val password = userDataStoreManager.passwordFlow.first()
            val result = client.get("${ApiConfig.BASE_URL}/volunteers/free"){
                headers {
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