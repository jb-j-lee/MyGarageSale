package com.myjb.dev.util

import android.view.View
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import com.myjb.dev.model.remote.datasource.APIResponse
import com.myjb.dev.model.remote.dto.BookInfoItem

@BindingAdapter("progressShow")
fun ContentLoadingProgressBar.bindShow(response: APIResponse?) {
    visibility = if (response is APIResponse.Loading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("imageShow")
fun ImageView.bindShow(response: APIResponse?) {
    visibility =
        if (response is APIResponse.Success<*> && (response.data as List<BookInfoItem>).isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
}