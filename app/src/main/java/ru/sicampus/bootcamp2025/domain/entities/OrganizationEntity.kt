package ru.sicampus.bootcamp2025.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class OrganizationEntity (
    val id: Long? = null,
    val name: String? = null,
    val address: String? = null,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val peopleCount: Long? = null
): Parcelable