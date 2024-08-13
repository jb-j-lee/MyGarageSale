package com.myjb.dev.view.recyclerView

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myjb.dev.model.data.Company
import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewBookinfoBinding
import com.myjb.dev.model.remote.dto.BookInfoItem
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class CardAdapter(var context: Context) : RecyclerView.Adapter<CardItemView>() {
    val BOOK_WIDTH: Int = 200

    interface OnItemClickListener {
        fun onItemClick(name: String?, isbn: String?, position: Int)
    }

    var itemList: MutableList<BookInfoItem> = ArrayList()

    var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemView {
        val binding =
            ItemRecyclerviewBookinfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardItemView(binding)
    }

    override fun onBindViewHolder(viewHolder: CardItemView, position: Int) {
        val item = itemList[position]

        if (item.company == Company.NONE) {
            return
        }

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

    override fun getItemViewType(position: Int): Int {
        if (itemList.isEmpty()) return 1

        return itemList[position].company.ordinal
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mOnItemClickListener = listener
    }

    fun updateView(items: List<BookInfoItem>?) {
        itemList.clear()
        itemList.addAll(items!!)
        notifyDataSetChanged()
    }

    fun clearView() {
        itemList.clear()
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
}