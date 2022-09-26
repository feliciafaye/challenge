package io.faye.music.discover

import androidx.paging.PagingData
import io.faye.music.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DiscoverViewState(
	val loading: Boolean = true,
	val sessions: Flow<PagingData<Session>> = emptyFlow()
)