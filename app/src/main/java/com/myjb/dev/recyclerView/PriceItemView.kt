package com.myjb.dev.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewPriceBinding
import com.myjb.dev.network.PriceItem

class PriceItemView(private val view: ItemRecyclerviewPriceBinding) :
    RecyclerView.ViewHolder(view.root) {

    fun bind(item: PriceItem) {
        view.price.text = item.price
        view.best.text = item.best
        view.better.text = item.better
        view.good.text = item.good
    }
}