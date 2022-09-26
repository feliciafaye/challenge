package io.faye.music.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import arrow.core.continuations.either
import arrow.core.getOrHandle
import io.faye.music.MusicApiClient

class SessionPagingSource(private val musicApiClient: MusicApiClient) : PagingSource<Int, Session>() {
	override fun getRefreshKey(state: PagingState<Int, Session>): Int = 0

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Session> = either {
		val currentKey = params.key ?: 0
		val sessions = musicApiClient.getFeed().bind().toSessions()

		val prev = if (currentKey == 0) {
			null
		} else {
			currentKey - 1
		}

		val next = if (currentKey < 5) {
			currentKey + 1
		} else {
			null
		}

		LoadResult.Page(sessions, prev, next)
	}
		.getOrHandle {
			LoadResult.Error(it)
		}

}