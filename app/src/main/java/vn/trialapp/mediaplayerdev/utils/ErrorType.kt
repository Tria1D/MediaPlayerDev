package vn.trialapp.mediaplayerdev.utils

enum class ErrorType(val type: String) {
    NO_CONNECTION("No Connection"),
    UNKNOWN("Unknown Error"),
    REQUEST_TIME_OUT("Request Timeout"),
    SERVER_ERROR("Server Error")
}