package vn.trialapp.mediaplayerdev.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.service.interceptors.MediaInterceptor
import vn.trialapp.mediaplayerdev.service.network.AuthApi
import vn.trialapp.mediaplayerdev.service.network.MediaApi
import vn.trialapp.mediaplayerdev.service.network.YoutubeApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    companion object {
        private const val BASE_URL = "https://api.spotify.com"
        private const val AUTH_URL = "https://accounts.spotify.com"
        private const val YOUTUBE_URL = "https://www.googleapis.com"
    }

    @Singleton
    @Provides
    fun provideMediaApi(@Named("mediaRetrofit") retrofit: Retrofit): MediaApi {
        return retrofit.create(MediaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApi(@Named("authRetrofit") retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideYoutubeApi(@Named("youtubeRetrofit") retrofit: Retrofit): YoutubeApi {
        return retrofit.create(YoutubeApi::class.java)
    }

    @Singleton
    @Provides
    @Named("mediaRetrofit")
    fun provideMediaRetrofit(@Named("mediaOkHttpClient") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named("authRetrofit")
    fun providesAuthRetrofit(@Named("authOkHttpClient") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named("youtubeRetrofit")
    fun providesYoutubeRetrofit(@Named("youtubeOkHttpClient") client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(YOUTUBE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("authOkHttpClient")
    fun provideAuthOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("mediaOkHttpClient")
    fun provideMediaOkHttpClient(
        logging: HttpLoggingInterceptor,
        mediaInterceptor: MediaInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(mediaInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("youtubeOkHttpClient")
    fun provideYoutubeOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideMediaInterceptor(
        dataStoreRepository: DataStoreRepository
    ): Interceptor = MediaInterceptor(dataStoreRepository)
}