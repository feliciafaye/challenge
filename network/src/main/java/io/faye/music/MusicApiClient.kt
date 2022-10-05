package io.faye.music

import arrow.core.Either
import io.faye.music.dto.DataDto
import io.faye.music.dto.MusicApiDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicApiClient @Inject constructor(private val httpClient: HttpClient) {
	suspend fun getFeed(): Either<Throwable, MusicApiDto> =
		Either.catch {
			httpClient.get("$BASE_URL/5df79a3a320000f0612e0115")
				.body()
		}


	suspend fun search(searchTerm: String): Either<Throwable, MusicApiDto> =
		Either.catch {
			httpClient.get("$BASE_URL/5df79b1f320000f4612e011e")
				.body<MusicApiDto>()
				.let { dto ->
					val searchTermLower = searchTerm.lowercase()

					val filteredSessions =
						dto.data.sessions.filter {
							it.name.lowercase().contains(searchTermLower) ||
									it.currentTrack.title.lowercase().contains(searchTermLower) ||
									it.genres.joinToString(separator = " ").lowercase().contains(searchTerm)
						}
					MusicApiDto(DataDto(filteredSessions.shuffled()))
				}
		}


	companion object {
		// TODO move this out
		private const val BASE_URL = "https://www.mocky.io/v2"
	}
}