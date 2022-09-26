package io.faye.music.dto

import kotlinx.serialization.Serializable

@Serializable
data class MusicApiDto(
	val data: DataDto
)