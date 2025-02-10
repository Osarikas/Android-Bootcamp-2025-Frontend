package ru.sicampus.bootcamp2025.domain.organizations

import ru.sicampus.bootcamp2025.domain.entities.OrganizationEntity

interface OrganizationListRepo {
    suspend fun getUsers(pageNum: Int, pageSize: Int): Result<List<OrganizationEntity>>
}