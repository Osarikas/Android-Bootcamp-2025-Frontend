package ru.sicampus.bootcamp2025.data.profile

import ru.sicampus.bootcamp2025.domain.entities.UserEntity
import ru.sicampus.bootcamp2025.domain.profile.ProfileRepo


class ProfileRepoImpl(
    private val profileNetworkDataSource: ViewProfileNetworkDataSource?,
    private val editProfileNetworkDataSource: EditProfileNetworkDataSource?
) : ProfileRepo{
    override suspend fun getProfile(): Result<UserEntity> {
        return profileNetworkDataSource?.getProfile()?.map{
                dto ->
            UserEntity(
                name = dto.name,
                photoUrl = dto.photoUrl,
                id = dto.id,
                email = dto.email,
                role = dto.role,
                birthDate = dto.birthDate,
                phoneNumber = dto.phoneNumber,
                telegramUsername = dto.telegramUsername,
                organizationName = dto.organizationName,
                about = dto.about
            )
        } ?: return Result.failure(IllegalStateException("Null ProfileNetworkDataSource"))
    }

    override suspend fun editProfile(entity: UserEntity): Result<UserEntity> {
        return editProfileNetworkDataSource?.editProfile(entity.toDTO())?.map {
                dto ->
            UserEntity(
                name = dto.name,
                photoUrl = dto.photoUrl,
                id = dto.id,
                email = dto.email,
                role = dto.role,
                birthDate = dto.birthDate,
                phoneNumber = dto.phoneNumber,
                telegramUsername = dto.telegramUsername,
                organizationName = dto.organizationName,
                about = dto.about
            )
        } ?: return Result.failure(IllegalStateException("Null EditProfileNetworkDataSource"))
    }
}