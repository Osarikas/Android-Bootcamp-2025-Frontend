package ru.sicampus.bootcamp2025.domain.login

interface LoginRepo {
    suspend fun login(email: String, password: String): Result<Unit>
}