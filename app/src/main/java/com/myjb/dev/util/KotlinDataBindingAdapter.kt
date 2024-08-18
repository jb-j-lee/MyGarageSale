package com.myjb.dev.util

import android.view.View
import android.widget.ImageView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.myjb.dev.model.remote.datasource.APIResponse
import com.myjb.dev.model.remote.dto.BookInfoItem
import com.myjb.dev.mygaragesale.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {
    if (url.isNotBlank()) {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = withContext(Dispatchers.IO) {
                Glide.with(this@setImageUrl)
                    .applyDefaultRequestOptions(
                        RequestOptions()
                            .format(DecodeFormat.PREFER_RGB_565)
                            .disallowHardwareConfig()
                    )
                    .asBitmap()
                    .load(url)
                    .placeholder(R.drawable.loading_book)
                    .error(R.drawable.ic_launcher_foreground)
                    .submit().get()
            }

            this@setImageUrl.setImageBitmap(bitmap)
        }
    } else {
        setImageResource(R.drawable.ic_launcher_foreground)
    }
}