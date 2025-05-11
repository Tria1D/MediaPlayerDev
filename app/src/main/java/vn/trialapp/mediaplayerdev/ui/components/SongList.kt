package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import vn.trialapp.mediaplayerdev.models.Song
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.Common.AliceBlue
import vn.trialapp.mediaplayerdev.ui.theme.Common.AzureishWhite
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme

@Composable
fun SongList(songs: List<Song>, onItemClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(songs) { index, song ->
            SongListItem(song, onClick = { onItemClick(index) })
        }
    }
}

@Composable
fun SongListItem(
    song: Song,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                AliceBlue,
                RoundedCornerShape(dimensionResource(R.dimen.mpd_song_list_item_row_container_rounded_corner_size))
            )
            .border(
                width = dimensionResource(R.dimen.mpd_song_list_item_row_container_border_width),
                color = AzureishWhite,
                shape = RoundedCornerShape(dimensionResource(R.dimen.mpd_song_list_item_row_container_rounded_corner_size))
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = song.imageUrl,
            contentDescription = song.title,
            modifier = Modifier
                .size(dimensionResource(R.dimen.mpd_song_list_item_image_size))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.mpd_song_list_item_image_rounded_corner_clip_size))),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.mpd_song_list_item_image_spacer_between_image_and_column)))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSongList() {
    val dummySongs = listOf(
        Song(
            mediaId = "1",
            title = "Imagine",
            artist = "John Lennon"
        ),
        Song(
            mediaId = "2",
            title = "Bohemian Rhapsody",
            artist = "Queen"
        )
    )
    MediaPlayerDevTheme {
        SongList(songs = dummySongs, onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSongListItem() {
    val dummySong = Song(
        mediaId = "1",
        title = "Imagine",
        artist = "John Lennon"
    )
    MediaPlayerDevTheme {
        SongListItem(song = dummySong, onClick = {})
    }
}