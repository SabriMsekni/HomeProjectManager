package org.isep.homeexchange.core.dto

import org.isep.homeexchange.infrastructure.dao.HousingDao

data class HousingDto(
    val id: Long,
    val title: String,
    val description: String,
    val userId: Long,
    val properties: List<PropertyDto>?,
    val images: List<ImageDto>
)

fun HousingDto.toDao() = HousingDao(
    id = id,
    description = description,
    title = title,
    user = null,
    properties = properties!!.toDao(),
    images = images.toDao(),
    userId = userId
)

fun List<HousingDto>.toDao() = map { it.toDao() }