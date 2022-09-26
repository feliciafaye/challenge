package io.faye.music.discover

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.faye.music.model.Session
import io.faye.music.model.Track
import io.faye.music.ui.theme.DSMusicTheme


@Composable
internal fun SessionItem(session: Session) {
	Box(
		modifier = Modifier
			.padding(8.dp)
			.clip(RoundedCornerShape(8.dp))
	) {
		AsyncImage(
			model = session.currentTrack.artworkUrl,
			contentDescription = "Current track image",
			contentScale = ContentScale.FillWidth,
			modifier = Modifier.fillMaxSize()
		)

		Row(
			modifier = Modifier
				.padding(8.dp)
				.clip(RoundedCornerShape(18.dp))
				.background(Color(.8f, .8f, .8f, .6f))
				.padding(end = 4.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "", tint = Color.Black)
			Spacer(modifier = Modifier.size(2.dp))
			Text(
				text = "${session.listeningCount}",
				color = Color.Black,
				fontWeight = FontWeight.Bold,
				fontSize = 12.sp,

				)

		}


		Column(
			modifier = Modifier
				.fillMaxWidth()
				.background(
					Brush.verticalGradient(
						listOf(
							Color(0f, 0f, 0f, .1f),
							Color(0f, 0f, 0f, .8f)
						)
					)
				)
				.align(Alignment.BottomStart)
				.padding(4.dp)
		) {
			val genres by derivedStateOf { session.genres.joinToString(separator = ", ") }

			Text(text = session.name, fontWeight = FontWeight.Bold)
			Text(text = genres, maxLines = 1, overflow = TextOverflow.Ellipsis)
		}
	}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SessionItemPreviewNight() {
	val session = Session(
		"A music session",
		100,
		listOf("genre 1", "genre 2"),
		Track("title", "http")
	)
	DSMusicTheme {
		SessionItem(session = session)
	}
}

@Preview
@Composable
fun SessionItemPreview() {
	val session = Session(
		"A music session",
		100,
		listOf("genre 1", "genre 2"),
		Track("title", "http")
	)
	DSMusicTheme {
		SessionItem(session = session)
	}
}



