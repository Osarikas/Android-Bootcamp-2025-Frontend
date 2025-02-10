package ru.sicampus.bootcamp2025.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrganizationDTO (
    @SerialName("id") val id: Long?,
    @SerialName("name") val name: String?,
    @SerialName("address") val address: String?,
    @SerialName("latitude") val latitude : Float?,
    @SerialName("longitude") val longitude : Float?,
    @SerialName("peopleCount") val peopleCount: Long?
)