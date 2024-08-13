package com.myjb.dev.util

import android.view.View

internal class OnSingleClickListener(private val onSingleClick: (View) -> Unit) :
    View.OnClickListener {
    companion object {
        private const val CLICK_INTERVAL = 500
    }

    private var lastClickedTime: Long = 0L

    override fun onClick(v: View) {
        if (isSingleClick()) {
            lastClickedTime = System.currentTimeMillis()
            onSingleClick(v)
        }
    }

    private fun isSingleClick(): Boolean {
        return System.currentTimeMillis() - lastClickedTime > CLICK_INTERVAL
    }
}