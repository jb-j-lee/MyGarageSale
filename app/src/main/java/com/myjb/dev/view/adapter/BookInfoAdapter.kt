package com.myjb.dev.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myjb.dev.model.remote.dto.BookInfoItem
import com.myjb.dev.mygaragesale.databinding.ItemBookinfoBinding

class BookInfoAdapter(private val context: Context) :
    ListAdapter<BookInfoItem, BookInfoAdapter.BookInfoViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookInfoViewHolder {
        val binding = ItemBookinfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return BookInfoViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: BookInfoViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class BookInfoViewHolder(private val binding: ItemBookinfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookInfoItem) {
            binding.model = item

            binding.executePendingBindings()
        }
    }
}

object DiffUtilCallback : DiffUtil.ItemCallback<BookInfoItem>() {
    override fun areItemsTheSame(oldItem: BookInfoItem, newItem: BookInfoItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: BookInfoItem, newItem: BookInfoItem): Boolean {
        return oldItem == newItem
    }
}