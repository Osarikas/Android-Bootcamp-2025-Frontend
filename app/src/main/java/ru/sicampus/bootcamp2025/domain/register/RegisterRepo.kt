package ru.sicampus.bootcamp2025.domain.register

import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.domain.entities.UserRegisterEntity

interface RegisterRepo {
    suspend fun register(registerDTO: UserRegisterEntity): Result<UserDTO>
}