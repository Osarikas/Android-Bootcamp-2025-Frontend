package ru.sicampus.bootcamp2025.data.volunteers
import ru.sicampus.bootcamp2025.domain.volunteers.free.FreeVolunteerRepo
import ru.sicampus.bootcamp2025.domain.entities.UserEntity

class FreeVolunteerRepoImpl(
    private val freeVolunteerNetworkDataSource: FreeVolunteerNetworkDataSource
) : FreeVolunteerRepo {
    override suspend fun getFreeVolunteers(): Result<List<UserEntity>> {
        return  freeVolunteerNetworkDataSource.getFreeVolunteers().map { listDto ->
            println("Dto list: $listDto")
            listDto.mapNotNull { dto ->
                UserEntity(
                    name = dto.name,
                    photoUrl = dto.photoUrl,
                    id = dto.id,
                    email = dto.email,
                    role = dto.role,
                    birthDate = dto.birthDate,
                    phoneNumber = dto.phoneNumber ?: return@mapNotNull null,
                    telegramUsername = dto.telegramUsername ?: return@mapNotNull null,
                    organizationName = dto.organizationName,
                    about = dto.about ?: return@mapNotNull null,
                )

            }

        }
    }
}