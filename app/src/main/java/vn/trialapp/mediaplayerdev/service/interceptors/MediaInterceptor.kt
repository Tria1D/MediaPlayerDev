package vn.trialapp.mediaplayerdev.service.interceptors

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import vn.trialapp.mediaplayerdev.di.IoDispatcher
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): Interceptor {

    private var authToken: String? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val auth = fetchAuth()
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer $auth")
            .method(original.method, original.body)
            .build()
        return chain.proceed(request)
    }

    private fun fetchAuth(): String {
        runBlocking {
            authToken = dataStoreRepository.fetchAuth()
        }
        return authToken?:""
    }
}