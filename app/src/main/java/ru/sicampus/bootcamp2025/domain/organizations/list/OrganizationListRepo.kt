package ru.sicampus.bootcamp2025.domain.organizations.list

import ru.sicampus.bootcamp2025.domain.entities.OrganizationEntity

interface OrganizationListRepo {
    suspend fun getUsers(pageNum: Int, pageSize: Int): Result<List<OrganizationEntity>>
}