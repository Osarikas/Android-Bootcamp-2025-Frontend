package ru.sicampus.bootcamp2025.domain.profile

class GetProfileUseCase(
    private val repo : ProfileRepo
) {
    suspend operator fun invoke() = repo.getProfile()
    }
