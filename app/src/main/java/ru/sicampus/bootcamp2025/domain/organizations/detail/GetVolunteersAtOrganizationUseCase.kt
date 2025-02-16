package ru.sicampus.bootcamp2025.domain.organizations.detail

class GetVolunteersAtOrganizationUseCase(private val repo: OrganizationDetailRepo) {
    suspend operator fun invoke(
        id: Int,
        pageNum: Int,
        pageSize: Int
    ) = repo.getVolunteersAtOrganization(id, pageNum, pageSize)
}