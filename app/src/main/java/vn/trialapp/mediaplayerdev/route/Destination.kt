package vn.trialapp.mediaplayerdev.route

sealed class Destination(val route: String) {
    object Main: Destination("main")
    object Secondary: Destination("secondary")
}
