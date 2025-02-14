package ru.sicampus.bootcamp2025.domain.entities

import ru.sicampus.bootcamp2025.data.dto.UserRegisterDTO

data class UserRegisterEntity (
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val telegramUsername: String = "",
    val about: String = ""
){
    fun toRegisterDTO(): UserRegisterDTO {
        return UserRegisterDTO(
            email = this.email,
            name = this.name,
            password = this.password,
            phoneNumber = this.phoneNumber,
            telegramUsername = this.telegramUsername,
            about = this.about
        )
    }
}