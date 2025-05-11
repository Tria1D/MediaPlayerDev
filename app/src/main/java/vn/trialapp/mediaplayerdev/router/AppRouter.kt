package vn.trialapp.mediaplayerdev.router

sealed class AppRouter(val route: String) {
    object Songs: AppRouter("songs")
    object SongDetail : AppRouter("songDetail/{index}") {
        fun createRoute(index: Int) = "songDetail/$index"
    }
}
