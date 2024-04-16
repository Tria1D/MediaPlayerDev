package vn.trialapp.mediaplayerdev.usecases

import android.content.Context
import android.util.SparseArray
import com.maxrave.kotlinyoutubeextractor.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import vn.trialapp.mediaplayerdev.di.IoDispatcher
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.utils.LogUtil
import javax.inject.Inject

class DownloadYTUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val dataStoreRepository: DataStoreRepository
) {

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
//                    dataStoreRepository.saveAudioUrl(it)
//                } ?: run {
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
                LogUtil.d("DownloadYTUseCase: ${audioYtFiles?.url}")
                audioYtFiles?.url
            }
            else -> {
                LogUtil.d("DownloadYTUseCase audioYtFiles is null")
                null
            }
        }
    }
}