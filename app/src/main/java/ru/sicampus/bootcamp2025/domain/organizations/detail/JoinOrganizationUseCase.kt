package ru.sicampus.bootcamp2025.domain.organizations.detail

class JoinOrganizationUseCase(private val repo: OrganizationDetailRepo) {
    suspend fun invoke(id: Int) = repo.joinOrganization(id)
}