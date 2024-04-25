package vn.trialapp.mediaplayerdev.usecases

import android.content.Context
import android.util.SparseArray
import com.maxrave.kotlinyoutubeextractor.*
import dagger.hilt.android.qualifiers.ApplicationContext
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.utils.LogUtil
import javax.inject.Inject

class DownloadYTUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): Pair<String?, String?> {
        val yt = YTExtractor(con = context, CACHING = true, LOGGING = true, retryCount = 3)
        var ytFiles: SparseArray<YtFile>? = null
        yt.extract(dataStoreRepository.fetchVideoId())
        return when (yt.state) {
            State.SUCCESS -> {
                ytFiles = yt.getYTFiles()
                val audioYtFiles = ytFiles?.getAudioOnly()?.bestQuality()
                val imageUrl = dataStoreRepository.fetchImageUrl()
                LogUtil.d("DownloadYTUseCase: audioYtFilesUrl = ${audioYtFiles?.url}, imageUrl = $imageUrl")
                Pair(audioYtFiles?.url, imageUrl)
            }
            else -> {
                LogUtil.d("DownloadYTUseCase audioYtFiles is null")
                Pair(null, null)
            }
        }
    }
}