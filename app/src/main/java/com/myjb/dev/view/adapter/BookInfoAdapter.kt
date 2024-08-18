package com.myjb.dev.view.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myjb.dev.model.remote.dto.BookInfoItem
import com.myjb.dev.mygaragesale.databinding.ItemBookinfoBinding
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class BookInfoAdapter(var context: Context) :
    ListAdapter<BookInfoItem, BookInfoViewHolder>(DiffUtilCallback) {
    val BOOK_WIDTH: Int = 200

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookInfoViewHolder {
        val binding = ItemBookinfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return BookInfoViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: BookInfoViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(item)

        Thread {
            val imageView = viewHolder.image
            val bitmap = getBitmap(item.image)
            if (bitmap != null) {
                (context as Activity).runOnUiThread {
                    val width = BOOK_WIDTH
                    val height = BOOK_WIDTH * bitmap.height / bitmap.width

                    imageView.minimumWidth = width
                    imageView.minimumHeight = height
                    imageView.setImageBitmap(bitmap)
                }
            }
        }.start()
    }

    //TODO Use cache control
    private fun getBitmap(imageUrl: String?): Bitmap? {
        var urlConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        try {
            val url = URL(imageUrl)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection!!.requestMethod = "GET"
            urlConnection.doInput = true

            inputStream = urlConnection.inputStream

            val bitmap = BitmapFactory.decodeStream(inputStream)
            return bitmap
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            urlConnection?.disconnect()
        }
        return null
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<BookInfoItem>() {
        override fun areItemsTheSame(oldItem: BookInfoItem, newItem: BookInfoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BookInfoItem, newItem: BookInfoItem): Boolean {
            return oldItem == newItem
        }
    }
}

class BookInfoViewHolder(private val binding: ItemBookinfoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val image = binding.image

    fun bind(item: BookInfoItem) {
        binding.model = item
        binding.executePendingBindings()
    }
}