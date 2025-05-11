package vn.trialapp.mediaplayerdev.utils

class AppConstants {

    object CommonConstants {
        const val MIN_RESULTS_FROM_YOUTUBE = 0
        const val MAX_RESULTS_FROM_YOUTUBE = 10
    }

    object MediaNotification {
        const val NOTIFICATION_ID = 200
        const val NOTIFICATION_CHANNEL_NAME = "notification channel 1"
        const val NOTIFICATION_CHANNEL_ID = "notification channel id 1"
    }

    object YoutubeKey {
        const val API_KEY = ""
    }

    object Firebase {
        const val SONG_COLLECTION = "songs"
    }

    object SongDetailScreen {
        const val INITIAL_PROGRESS_STRING = "00:00"
        const val INITIAL_DURATION = 0L
        const val INITIAL_PROGRESS = 0f
        const val INITIAL_IMAGE_URL = ""
    }

    object Common {
        const val INITIAL_ROTATION = 0f
        const val TARGET_ROTATION_PLAYING = 360f
        const val TARGET_ROTATION_SLOW_DOWN = 20
        const val TWEEN_PLAYING_DURATION = 8000
        const val TWEEN_SLOW_DOWN_DURATION = 8000
        const val INITIAL_SEARCH_TEXT = ""
        const val INITIAL_ITEMS_SEARCHED = ""
        const val INITIAL_PLAYER_BAR_PROGRESS = 0f
    }
}