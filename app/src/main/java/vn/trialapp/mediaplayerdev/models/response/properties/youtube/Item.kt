package vn.trialapp.mediaplayerdev.models.response.properties.youtube

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)