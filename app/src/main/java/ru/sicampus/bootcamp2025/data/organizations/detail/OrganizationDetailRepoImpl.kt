package ru.sicampus.bootcamp2025.data.organizations.detail

import ru.sicampus.bootcamp2025.domain.entities.UserEntity
import ru.sicampus.bootcamp2025.domain.organizations.detail.OrganizationDetailRepo

class OrganizationDetailRepoImpl : OrganizationDetailRepo {
    override suspend fun joinOrganization(id: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getVolunteersAtOrganization(
        id: Int,
        pageNum: Int,
        pageSize: Int
    ): Result<List<UserEntity>> {
        TODO("Not yet implemented")
    }
}