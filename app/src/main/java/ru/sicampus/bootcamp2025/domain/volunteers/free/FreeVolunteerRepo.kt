package ru.sicampus.bootcamp2025.domain.volunteers.free

import ru.sicampus.bootcamp2025.domain.entities.UserEntity

interface FreeVolunteerRepo {
    suspend fun getFreeVolunteers(): Result<List<UserEntity>>
}