package com.myjb.dev.view.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewBookBinding

class BookItemView(private val view: ItemRecyclerviewBookBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun bind(item: String) {
        view.book.text = item
    }
}