package com.myjb.dev.view.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewBookinfoBinding
import com.myjb.dev.model.remote.dto.BookInfoItem
import com.myjb.dev.util.setOnSingleClickListener

class CardItemView(private val view: ItemRecyclerviewBookinfoBinding) :
    RecyclerView.ViewHolder(view.root) {
    val image = view.image

    fun bind(item: BookInfoItem) {
//        Animation animation = AnimationUtils . loadAnimation (getContext(), android.R.anim.slide_in_left);
//        startAnimation(animation);

        view.name.text = item.name
        //TODO
        view.save.setOnSingleClickListener { }

        view.price.text = item.price.price
        view.best.text = item.price.best
        view.better.text = item.price.better
        view.good.text = item.price.good
    }
}