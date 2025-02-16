package ru.sicampus.bootcamp2025.data.organizations.list

import ru.sicampus.bootcamp2025.domain.entities.OrganizationEntity
import ru.sicampus.bootcamp2025.domain.organizations.list.OrganizationListRepo

class OrganizationListRepoImpl(
    private val dataSource: OrganizationListNetworkDataSource
) : OrganizationListRepo {
    override suspend fun getUsers(
        pageNum: Int,
        pageSize: Int
    ): Result<List<OrganizationEntity>> {
        return dataSource.getOrganizations(pageNum, pageSize).map { pagingDTO ->
            println(pagingDTO)
            pagingDTO.content?.mapNotNull { dto ->
                println(dto)
                OrganizationEntity(
                    id = dto.id ?: return@mapNotNull null,
                    name = dto.name ?: return@mapNotNull null,
                    address = dto.address ?: return@mapNotNull null,
                    latitude = dto.latitude ?: return@mapNotNull null,
                    longitude = dto.longitude ?: return@mapNotNull null,
                    peopleCount = dto.peopleCount ?: return@mapNotNull null
                ) } ?: return Result.failure(IllegalStateException("List parse error"))


        }
    }

}