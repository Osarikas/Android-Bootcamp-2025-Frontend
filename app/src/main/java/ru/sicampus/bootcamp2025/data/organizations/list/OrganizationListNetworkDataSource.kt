package ru.sicampus.bootcamp2025.data.organizations.list

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sicampus.bootcamp2025.data.ApiConfig
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import ru.sicampus.bootcamp2025.data.dto.OrganizationsListPagingDTO

class OrganizationListNetworkDataSource(
    context: Context
) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    private val userDataStoreManager = UserDataStoreManager.getInstance(context)

    suspend fun getOrganizations(pageNum: Int, pageSize: Int): Result<OrganizationsListPagingDTO> =
        withContext(Dispatchers.IO) {
            runCatching {
                val email = userDataStoreManager.emailFlow.first()
                val password = userDataStoreManager.passwordFlow.first()
                val result = client.get("${ApiConfig.BASE_URL}/centers?page=$pageNum&size=$pageSize"){
                    headers{
                        basicAuth(email, password)
                    }
                }
                if (result.status != HttpStatusCode.OK) {
                    println(result.bodyAsText())
                    error("Status ${result.status}")
                }
                println(result.bodyAsText())
                println("Parsed organizations: ${result.body<OrganizationsListPagingDTO>()}")
                result.body()
            }
        }

}