package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.trialapp.mediaplayerdev.ui.theme.DenimBlue

@Composable
fun HeaderBackground() {
    Box(modifier = Modifier
        .clip(
            RoundedCornerShape(
                bottomStart = 60.dp,
                bottomEnd = 60.dp
            )
        )
        .heightIn(100.dp)
        .fillMaxWidth()
        .background(Color.Black)
    )
}

@Preview
@Composable
private fun PreviewHeaderBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DenimBlue)
    ) {
        HeaderBackground()
    }
}