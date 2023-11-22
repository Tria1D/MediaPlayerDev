package vn.trialapp.mediaplayerdev.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.repository.local.impl.DataStoreImpl
import vn.trialapp.mediaplayerdev.repository.remote.AuthRepository
import vn.trialapp.mediaplayerdev.repository.remote.MediaRepository
import vn.trialapp.mediaplayerdev.repository.remote.YoutubeRepository
import vn.trialapp.mediaplayerdev.repository.remote.impl.AuthRepositoryImpl
import vn.trialapp.mediaplayerdev.repository.remote.impl.MediaRepositoryImpl
import vn.trialapp.mediaplayerdev.repository.remote.impl.YoutubeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindDataStore(dataStoreImpl: DataStoreImpl): DataStoreRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    abstract fun bindMediaRepository(mediaRepositoryImpl: MediaRepositoryImpl): MediaRepository

    @Singleton
    @Binds
    abstract fun bindYoutubeRepository(youtubeRepositoryImpl: YoutubeRepositoryImpl): YoutubeRepository
}