package ru.sicampus.bootcamp2025.domain.profile

import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.domain.entities.UserEntity

interface ProfileRepo {
    suspend fun getProfile(): Result<UserEntity>
    suspend fun editProfile(entity: UserEntity): Result<UserEntity>

}