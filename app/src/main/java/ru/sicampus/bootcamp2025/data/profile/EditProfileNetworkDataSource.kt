package ru.sicampus.bootcamp2025.data.profile

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.sicampus.bootcamp2025.data.ApiConfig
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import ru.sicampus.bootcamp2025.data.dto.UserDTO

class EditProfileNetworkDataSource(context: Context) {
    private val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    private val userDataStoreManager = UserDataStoreManager.getInstance(context)
    suspend fun editProfile(userDTO: UserDTO): Result<UserDTO> = withContext(Dispatchers.IO){
        val jsonString = Json.encodeToString(userDTO)
        println("Sending JSON: $jsonString")
        runCatching {
            val email = userDataStoreManager.emailFlow.first()
            val password = userDataStoreManager.passwordFlow.first()
            val result = client.put("${ApiConfig.BASE_URL}/volunteers/profile"){
                headers{
                    basicAuth(email, password)
                    append("Content-Type", "application/json")
                }
                setBody(jsonString)
            }
            if (result.status != HttpStatusCode.OK) {
                error("Status ${result.status}")
            }
            result.body()
        }

    }
}