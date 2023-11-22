package vn.trialapp.mediaplayerdev.usecases

import android.content.Context
import android.util.Log
import android.util.SparseArray
import com.maxrave.kotlinyoutubeextractor.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import vn.trialapp.mediaplayerdev.di.IoDispatcher
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import javax.inject.Inject

class DownloadYTUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val dataStoreRepository: DataStoreRepository
) {
    private val TAG = "DownloadYTUseCase"

//    suspend operator fun invoke() {
//        val yt = YTExtractor(con = context, CACHING = true, LOGGING = true, retryCount = 3)
//        var ytFiles: SparseArray<YtFile>?
//        CoroutineScope(dispatcher).launch {
//            yt.extract(dataStoreRepository.fetchVideoId())
//            //Before get YtFile or VideoMeta, you need to check state of yt object
//            if (yt.state == State.SUCCESS) {
//                ytFiles = yt.getYTFiles()
//                val audioYtFiles = ytFiles?.getAudioOnly()?.bestQuality()
//                audioYtFiles?.url?.let {
//                    Log.d(TAG, "DownloadYTUseCase: $it")
//                    dataStoreRepository.saveAudioUrl(it)
//                } ?: run {
//                    Log.d(TAG, "DownloadYTUseCase audioYtFiles is null")
//                }
//            }
//        }
//    }
    suspend operator fun invoke(): String? {
        val yt = YTExtractor(con = context, CACHING = true, LOGGING = true, retryCount = 3)
        var ytFiles: SparseArray<YtFile>? = null
        yt.extract(dataStoreRepository.fetchVideoId())
        return when (yt.state) {
            State.SUCCESS -> {
                ytFiles = yt.getYTFiles()
                val audioYtFiles = ytFiles?.getAudioOnly()?.bestQuality()
                Log.d(TAG, "DownloadYTUseCase: ${audioYtFiles?.url}")
                audioYtFiles?.url
            }
            else -> {
                Log.d(TAG, "DownloadYTUseCase audioYtFiles is null")
                null
            }
        }
    }
}