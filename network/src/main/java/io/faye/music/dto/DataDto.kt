package io.faye.music.dto

import kotlinx.serialization.Serializable

@Serializable
data class DataDto(
	val sessions: List<SessionDto>
)
