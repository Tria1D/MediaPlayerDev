package vn.trialapp.mediaplayerdev.utils

import kotlin.random.Random


class AppUtils {

    public fun getRandomInt(from: Int, to: Int): Int = Random.Default.nextInt(from, to)

}