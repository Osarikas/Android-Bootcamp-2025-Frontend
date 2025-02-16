package ru.sicampus.bootcamp2025.domain.entities
import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.data.dto.UserRegisterDTO
import java.util.Date

data class UserEntity (
    val id: Long? = null,
    var email: String? = null,
    var name: String? = null,
    val role: String? = null,
    val birthDate: Date? = null,
    val phoneNumber: String? = null,
    val telegramUsername: String? = null,
    val organizationName: String? = null,
    val about: String? = null,
    val photoUrl: String? = null
){
    fun toDTO(): UserDTO {
        return UserDTO(
            id = id,
            email = email,
            name = name,
            role = role,
            birthDate = birthDate,
            phoneNumber = phoneNumber,
            telegramUsername = telegramUsername,
            organizationName = organizationName,
            about = about,
            photoUrl = photoUrl
        )
    }
}