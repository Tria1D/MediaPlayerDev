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
import vn.trialapp.mediaplayerdev.route.Destination
import vn.trialapp.mediaplayerdev.screens.main.MainScreen
import vn.trialapp.mediaplayerdev.screens.secondary.SecondaryScreen
import vn.trialapp.mediaplayerdev.service.media.MediaService
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.viewmodels.MediaViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mediaViewModel: MediaViewModel by viewModels()
    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.traceIn()
        super.onCreate(savedInstanceState)
        setContent {
            MediaPlayerDevTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Destination.Main.route) {

                    composable(Destination.Main.route) {
                        MainScreen(
                            mediaViewModel = mediaViewModel,
                            navController = navController,
                            startService = ::startService
                        ) }

                    composable(Destination.Secondary.route) {
                        SecondaryScreen(mediaViewModel = mediaViewModel)
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