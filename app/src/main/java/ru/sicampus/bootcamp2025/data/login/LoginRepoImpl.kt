package ru.sicampus.bootcamp2025.data.login

import ru.sicampus.bootcamp2025.domain.login.LoginRepo

class LoginRepoImpl(
    private val dataSource: LoginNetworkDataSource
) : LoginRepo {
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return dataSource.login(email, password)
    }
}