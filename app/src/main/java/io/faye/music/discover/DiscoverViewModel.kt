package io.faye.music.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.faye.music.model.MusicRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DiscoverViewModel @Inject constructor(
	private val musicRepository: MusicRepository
) : ViewModel() {

	private val _searchTermState = MutableStateFlow("")
	val searchTermState = _searchTermState.asStateFlow()

	val state = searchTermState
		.debounce(250)
		.map { searchTerm ->
			val sessionPagingSource = musicRepository
				.getFeed(searchTerm)
			DiscoverViewState(loading = false, sessions = sessionPagingSource)
		}
		.onStart {
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = DEFAULT_STATE
		)

	fun onSearchTermChanged(searchTerm: String) {
		_searchTermState.value = searchTerm
	}

	companion object {
		val DEFAULT_STATE = DiscoverViewState()
	}

}