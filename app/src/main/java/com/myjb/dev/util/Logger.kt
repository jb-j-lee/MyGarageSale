package com.myjb.dev.util

import android.util.Log
import com.myjb.dev.mygaragesale.BuildConfig

object Logger {
    fun i(tag: String, text: String) {
        if (!BuildConfig.DEBUG) {
            return
        }

        Log.i(tag, text)
    }

    fun e(tag: String, text: String) {
        if (!BuildConfig.DEBUG) {
            return
        }

        Log.e(tag, text)
    }
}