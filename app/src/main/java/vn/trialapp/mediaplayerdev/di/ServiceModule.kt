package vn.trialapp.mediaplayerdev.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import vn.trialapp.mediaplayerdev.models.remote.MusicDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMusicDatabase() = MusicDatabase()
}