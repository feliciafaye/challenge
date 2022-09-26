package io.faye.music.model

data class Session(
	val name: String,
	val listeningCount: Int,
	val genres: List<String>,
	val currentTrack: Track
)
