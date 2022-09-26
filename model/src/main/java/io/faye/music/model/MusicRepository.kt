package io.faye.music.model

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import arrow.core.continuations.either
import arrow.core.getOrHandle
import io.faye.music.MusicApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
	private val musicApiClient: MusicApiClient
) {
	suspend fun getFeed(searchTerm: String): Flow<PagingData<Session>> =
		if (searchTerm.isBlank()) {
			Pager(
				config = PagingConfig(PAGING_SIZE),
				initialKey = 0,
				pagingSourceFactory = { SessionPagingSource(musicApiClient) }
			).flow
		} else {
			flow {
				val pagingData = either {
					val sessions = musicApiClient.search(searchTerm).bind().toSessions()
					PagingData.from(
						data = sessions,
						sourceLoadStates = LoadStates(
							refresh = LoadState.NotLoading(true),
							prepend = LoadState.NotLoading(true),
							append = LoadState.NotLoading(true)
						)
					)
				}
					.getOrHandle {
						PagingData.empty(
							sourceLoadStates = LoadStates(
								refresh = LoadState.Error(it),
								prepend = LoadState.Error(it),
								append = LoadState.Error(it)
							)
						)
					}
				emit(pagingData)
			}
		}

	companion object {
		private const val PAGING_SIZE = 10
	}
}