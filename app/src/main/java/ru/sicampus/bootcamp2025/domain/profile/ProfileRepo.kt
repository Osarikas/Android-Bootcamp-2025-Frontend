package ru.sicampus.bootcamp2025.domain.profile

import ru.sicampus.bootcamp2025.data.dto.UserDTO

interface ProfileRepo {
    suspend fun getProfile(): Result<UserDTO>

}