package ru.sicampus.bootcamp2025.domain.profile

import ru.sicampus.bootcamp2025.domain.entities.UserEntity

class EditProfileUseCase(
    private val repo : ProfileRepo
) {
    suspend operator fun invoke(userEntity: UserEntity) = repo.editProfile(userEntity)
}