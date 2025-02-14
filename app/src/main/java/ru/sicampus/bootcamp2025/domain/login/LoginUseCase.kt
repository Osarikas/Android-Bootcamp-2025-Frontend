package ru.sicampus.bootcamp2025.domain.login

class LoginUseCase(
    private val repo: LoginRepo
) {
    suspend operator fun invoke(email: String, password: String) = repo.login(email, password)
}