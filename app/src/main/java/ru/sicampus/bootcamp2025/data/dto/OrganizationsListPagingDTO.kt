package ru.sicampus.bootcamp2025.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrganizationsListPagingDTO(
    @SerialName("content")
    val content: List<OrganizationDTO>?
)
