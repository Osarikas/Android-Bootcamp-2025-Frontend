package ru.sicampus.bootcamp2025.domain.organizations.detail

import ru.sicampus.bootcamp2025.domain.entities.UserEntity

interface OrganizationDetailRepo {
    suspend fun joinOrganization(id: Int): Result<Unit>
    suspend fun getVolunteersAtOrganization(
        id: Int,
        pageNum: Int,
        pageSize: Int
    ): Result<List<UserEntity>>
}