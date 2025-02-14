package ru.sicampus.bootcamp2025.data.register

import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.domain.entities.UserRegisterEntity
import ru.sicampus.bootcamp2025.domain.register.RegisterRepo

class RegisterRepoImpl(
    private val registerNetworkDataSource: RegisterNetworkDataSource
) : RegisterRepo {
    override suspend fun register(registerEntity: UserRegisterEntity): Result<UserDTO> {
        return registerNetworkDataSource.register(registerEntity.toRegisterDTO())
    }
}