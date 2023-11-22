package vn.trialapp.mediaplayerdev.models.response

import vn.trialapp.mediaplayerdev.models.response.properties.youtube.Item
import vn.trialapp.mediaplayerdev.models.response.properties.youtube.PageInfo

data class SearchYTResponse(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)