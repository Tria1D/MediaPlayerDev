package vn.trialapp.mediaplayerdev.models.response

import vn.trialapp.mediaplayerdev.models.response.properties.spotify.Tracks

data class SearchResponse(
    val tracks: Tracks
)