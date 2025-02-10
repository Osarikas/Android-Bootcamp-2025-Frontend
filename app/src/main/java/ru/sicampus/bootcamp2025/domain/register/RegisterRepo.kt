package ru.sicampus.bootcamp2025.domain.register

import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.data.dto.UserRegisterDTO

interface RegisterRepo {
    suspend fun register(registerDTO: UserRegisterDTO): Result<UserDTO>
}