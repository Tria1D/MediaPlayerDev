package vn.trialapp.mediaplayerdev.models.response.properties.youtube

import vn.trialapp.mediaplayerdev.models.response.properties.youtube.Default
import vn.trialapp.mediaplayerdev.models.response.properties.youtube.High
import vn.trialapp.mediaplayerdev.models.response.properties.youtube.Medium

data class Thumbnails(
    val default: Default,
    val high: High,
    val medium: Medium
)