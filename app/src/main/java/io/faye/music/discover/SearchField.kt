package io.faye.music.discover

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.faye.music.ui.theme.DSMusicTheme

@Composable
fun SearchField(
	searchTerm: String,
	modifier: Modifier = Modifier,
	onSearchTermChanged: (String) -> Unit
) {
	TextField(
		value = searchTerm,
		textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
		onValueChange = { onSearchTermChanged(it) },
		leadingIcon = {
			Icon(
				imageVector = Icons.Default.Search,
				contentDescription = "",
				tint = MaterialTheme.colors.onSurface
			)
		},
		trailingIcon = {
			if (searchTerm.isNotBlank()) {
				IconButton(onClick = { onSearchTermChanged("") }) {
					Icon(imageVector = Icons.Default.Close, contentDescription = "")
				}
			}
		},
		placeholder = {
			Text(
				text = "Search",
				color = MaterialTheme.colors.onSurface
			)
		},
		modifier = modifier
			.clip(RoundedCornerShape(8.dp))
			.background(MaterialTheme.colors.surface)
	)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun EmptySearchFieldPreviewNight() {
	DSMusicTheme {
		SearchField(searchTerm = "") {}
	}
}

@Preview
@Composable
fun EmptySearchFieldPreviewLight() {
	DSMusicTheme {
		SearchField(searchTerm = "") {}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SearchFieldPreviewNight() {
	DSMusicTheme {
		SearchField(searchTerm = "My Term") {}
	}
}

@Preview
@Composable
fun SearchFieldPreview() {
	DSMusicTheme {
		SearchField(searchTerm = "My Term") {}
	}
}