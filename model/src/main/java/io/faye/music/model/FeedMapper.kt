package io.faye.music.model

import io.faye.music.dto.MusicApiDto
import io.faye.music.dto.SessionDto
import io.faye.music.dto.TrackDto

fun MusicApiDto.toSessions(): List<Session> =
	data.sessions.map {
		it.toSession()
	}


private fun SessionDto.toSession(): Session =
	Session(
		name = name,
		listeningCount = listeningCount,
		genres = genres,
		currentTrack = currentTrack.toTrack()
	)

private fun TrackDto.toTrack(): Track =
	Track(
		title,
		artworkUrl
	)
