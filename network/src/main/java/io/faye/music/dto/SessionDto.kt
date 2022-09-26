package io.faye.music.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
	val name: String,
	@SerialName("listener_count")
	val listeningCount: Int,
	val genres: List<String>,
	@SerialName("current_track")
	val currentTrack: TrackDto
)
