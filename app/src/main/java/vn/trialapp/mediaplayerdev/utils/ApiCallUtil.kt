package vn.trialapp.mediaplayerdev.utils

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

private const val TAG = "safeApiCall"

internal suspend fun <ResponseType> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> ResponseType
): ResultStatus<ResponseType> = withContext(dispatcher) {
    try {
        val responseBody = apiCall.invoke()

        responseBody?.let { nonNullResponseBody ->
            Log.d(TAG, "Response: $nonNullResponseBody")
            ResultStatus.Success(nonNullResponseBody)
        } ?: run {
            ResultStatus.Error(ErrorType.SERVER_ERROR.type)
        }
    } catch (e: Exception) {
        when (e) {
            is SocketTimeoutException -> {
                ResultStatus.Error(ErrorType.REQUEST_TIME_OUT.type)
            }

            else -> {
                ResultStatus.Error(ErrorType.UNKNOWN.type)
            }
        }
    }
}