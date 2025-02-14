package ru.sicampus.bootcamp2025.domain.register

import ru.sicampus.bootcamp2025.domain.entities.UserRegisterEntity

class RegisterUseCase(
    private val registerRepo: RegisterRepo
) {
    suspend operator fun invoke(userEntity: UserRegisterEntity) = registerRepo.register(userEntity)
}
