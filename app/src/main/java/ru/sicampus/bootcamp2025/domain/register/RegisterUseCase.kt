package ru.sicampus.bootcamp2025.domain.register

import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.data.dto.UserRegisterDTO
import ru.sicampus.bootcamp2025.domain.entities.UserRegisterEntity

class RegisterUseCase(
    private val registerRepo: RegisterRepo
) {
    suspend operator fun invoke(userEntity: UserRegisterEntity) : Result<UserDTO> {
        val userDTO = UserRegisterDTO(
            name = userEntity.name.toString(),
            email = userEntity.email.toString(),
            password = userEntity.password.toString(),
            phoneNumber = userEntity.phoneNumber,
            telegramUsername = userEntity.telegramUsername,
            about = userEntity.about
        )
        return registerRepo.register(userDTO)
    }
}