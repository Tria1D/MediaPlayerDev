package vn.trialapp.mediaplayerdev

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import vn.trialapp.mediaplayerdev.router.AppRouter
import vn.trialapp.mediaplayerdev.features.songdetail.SongDetailScreen
import vn.trialapp.mediaplayerdev.features.songs.SongsScreen
import vn.trialapp.mediaplayerdev.service.media.MediaService
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.utils.LogUtil

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.traceIn()
        super.onCreate(savedInstanceState)
        setContent {
            MediaPlayerDevTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = AppRouter.Songs.route) {
                    composable(AppRouter.Songs.route) {
                        SongsScreen(navController = navController)
                    }
                    composable(AppRouter.SongDetail.route) { backStackEntry ->
                        val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
                        SongDetailScreen(index = index, startService = ::startService)
                    }
                }
            }
        }
        LogUtil.traceOut()
    }

    override fun onDestroy() {
        LogUtil.traceIn()
        super.onDestroy()
        stopService(Intent(this, MediaService::class.java))
        isServiceRunning = false
        LogUtil.traceOut()
    }

    private fun startService() {
        LogUtil.traceIn()
        if (!isServiceRunning) {
            val intent = Intent(this, MediaService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
        LogUtil.traceOut()
    }
}