package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme

@Composable
fun BackgroundImageUi(
    imageUrl: String
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        filterQuality = FilterQuality.Low,
        alpha = 0.5f,
    )
}

@Preview
@Composable
private fun PreviewBackgroundImageUi() {
    MediaPlayerDevTheme {
        BackgroundImageUi(imageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fcloudinary.com%2Fglossary%2Fimage-url&psig=AOvVaw3Um_B_9TXyaZ8_PZ27TIdo&ust=1716564187353000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCKCS_9yJpIYDFQAAAAAdAAAAABAI")
    }
}