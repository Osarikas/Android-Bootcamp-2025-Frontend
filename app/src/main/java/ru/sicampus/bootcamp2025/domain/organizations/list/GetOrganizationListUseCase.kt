package ru.sicampus.bootcamp2025.domain.organizations.list

import ru.sicampus.bootcamp2025.domain.entities.OrganizationEntity

class GetOrganizationListUseCase(
    private val repo: OrganizationListRepo
) {
    suspend operator fun invoke(pageNum: Int, pageSize: Int) : Result<List<OrganizationEntity>> {
        return repo.getUsers(pageNum, pageSize)
    }
}