package io.faye.music.discover

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import io.faye.music.model.Session
import io.faye.music.model.Track
import io.faye.music.ui.theme.DSMusicTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random
import kotlin.random.nextUInt

@Composable
fun DiscoverScreen(viewModel: DiscoverViewModel) {

	val state by viewModel.state.collectAsState(initial = DiscoverViewModel.DEFAULT_STATE)
	val searchTerm by viewModel.searchTermState.collectAsState(initial = "")

	DiscoverLayout(
		loading = state.loading,
		sessionsFlow = state.sessions,
		searchTerm,
		viewModel::onSearchTermChanged
	)
}

private val MAX_HEADER_SIZE = 200.dp
private val MIN_HEADER_SIZE = 55.dp

private val MAX_TITLE_SIZE = 48.sp
private val MIN_TITLE_SIZE = 18.sp

private const val GRID_SIZE = 2

@Composable
fun DiscoverLayout(
	loading: Boolean,
	sessionsFlow: Flow<PagingData<Session>>,
	searchTerm: String,
	onSearchTermChanged: (String) -> Unit
) {

	val sessions = sessionsFlow.collectAsLazyPagingItems()

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		AnimatedVisibility(visible = sessions.loadState.refresh is LoadState.Error) {
			Box(
				modifier = Modifier
					.clickable {
						sessions.retry()
					}
					.fillMaxWidth()
					.background(Color.Red)
					.height(40.dp)
					.padding(start = 8.dp),
				contentAlignment = Alignment.CenterStart
			) {
				Text(text = "Error loading feed!")
			}
		}

		val lazyGridState = rememberLazyGridState()

		val currentDensity = LocalDensity.current
		val headerHeight by remember {
			derivedStateOf {
				if (lazyGridState.firstVisibleItemIndex == 0) {
					val scrollOffset = lazyGridState.firstVisibleItemScrollOffset

					val offsetDp = with(currentDensity) {
						scrollOffset.toDp()
					}

					MAX_HEADER_SIZE - offsetDp.coerceAtMost(MAX_HEADER_SIZE - MIN_HEADER_SIZE)
				} else {
					MIN_HEADER_SIZE
				}

			}
		}
		val titleSize by remember {
			derivedStateOf {
				val percentage = headerHeight / MAX_HEADER_SIZE
				((MAX_TITLE_SIZE.value * percentage).coerceAtLeast(MIN_TITLE_SIZE.value)).sp
			}
		}

		var columnWidth by remember { mutableStateOf(0) }

		Column(
			modifier = Modifier
				.fillMaxWidth()
				.height(headerHeight)
				.background(MaterialTheme.colors.primaryVariant)
				.padding(16.dp),
			verticalArrangement = Arrangement.Bottom
		) {
			Text(
				text = "Discover",
				fontSize = titleSize,
				fontWeight = FontWeight.Bold,
				modifier = Modifier
					.onGloballyPositioned { coordinates ->
						columnWidth = coordinates.size.width
					}
					.graphicsLayer {
						// TODO maybe? Add translation
						// val percentage = headerHeight / MAX_HEADER_SIZE
						// translationX = (columnWidth / 2  - (columnWidth * (1f - percentage))).coerceAtLeast(0f)
						// translationX = (columnWidth * ((1f - percentage))) // .coerceAtMost(columnWidth / 2f)
					}
			)
			AnimatedVisibility(visible = lazyGridState.firstVisibleItemIndex == 0 && lazyGridState.firstVisibleItemScrollOffset == 0) {
				Spacer(modifier = Modifier.size(16.dp))
				SearchField(
					searchTerm = searchTerm,
					onSearchTermChanged = onSearchTermChanged,
					modifier = Modifier.fillMaxWidth()
				)
			}
		}

		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colors.surface),
			contentAlignment = Alignment.Center
		) {
			Log.i("XXX", "$loading   ${sessions.loadState.refresh}")
			if (loading || sessions.loadState.refresh == LoadState.Loading) {
				CircularProgressIndicator()
			}

			LazyVerticalGrid(
				state = lazyGridState,
				columns = GridCells.Fixed(GRID_SIZE),
				contentPadding = PaddingValues(8.dp),
				modifier = Modifier.fillMaxSize()
			) {
				items(lazyPagingItems = sessions) { session ->
					session?.let {
						SessionItem(session = it)
					}
				}
				if (sessions.loadState.append == LoadState.Loading) {
					item(span = { GridItemSpan(GRID_SIZE) }) {
						Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
							CircularProgressIndicator()
						}
					}
				}
			}
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DiscoverLayoutPreviewEmptyFeedNight() {
	DSMusicTheme {
		DiscoverLayout(loading = false, sessionsFlow = emptyFlow(), searchTerm = "") {}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DiscoverLayoutPreviewEmptyFeedLoadingNight() {
	DSMusicTheme {
		DiscoverLayout(loading = true, sessionsFlow = emptyFlow(), searchTerm = "") {}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DiscoverLayoutPreviewFeedNight() {
	val sessions = (1..10).map {
		Session(
			name = "Session $it",
			listeningCount = Random.nextUInt(10U, 300U).toInt(),
			genres = listOf("Genre 1", "Genre 2"),
			currentTrack = Track("Track $it", "http://$it")
		)
	}

	val pagingData = PagingData.from(
		data = sessions, sourceLoadStates = LoadStates(
			refresh = LoadState.NotLoading(false),
			prepend = LoadState.NotLoading(false),
			append = LoadState.NotLoading(false)
		)
	)

	DSMusicTheme {
		DiscoverLayout(loading = false, sessionsFlow = flowOf(pagingData), searchTerm = "") {}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DiscoverLayoutPreviewHasErrorNight() {
	val sessionsFlow = flowOf(
		PagingData.empty<Session>(
			sourceLoadStates = LoadStates(
				LoadState.Error(RuntimeException()),
				LoadState.Error(RuntimeException()),
				LoadState.Error(RuntimeException())
			)
		)
	)
	DSMusicTheme {
		DiscoverLayout(loading = false, sessionsFlow = sessionsFlow, searchTerm = "") {}
	}
}