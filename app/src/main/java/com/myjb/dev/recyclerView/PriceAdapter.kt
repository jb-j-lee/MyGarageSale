package com.myjb.dev.recyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myjb.dev.mygaragesale.R
import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewBookBinding
import com.myjb.dev.mygaragesale.databinding.ItemRecyclerviewPriceBinding
import com.myjb.dev.network.PriceItem

class PriceAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(name: String?, isbn: String?, position: Int)
    }

    internal enum class VIEW_TYPE {
        BOOK, PRICE
    }

    val names: Array<String> by lazy {
        context.resources.getStringArray(R.array.book_name)
    }

    val isbn: Array<String> by lazy {
        context.resources.getStringArray(R.array.book_isbn)
    }

    var itemList: ArrayList<PriceItem> = ArrayList()

    var mOnItemClickListener: OnItemClickListener? = null

    init {
        itemList = ArrayList(names.size * 2)

        //TODO
        for (i in names.indices) {
            val item = PriceItem(arrayOf("0"))
            itemList.add(2 * i, item)
            itemList.add(2 * i + 1, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE.BOOK.ordinal) {
            val binding =
                ItemRecyclerviewBookBinding.inflate(LayoutInflater.from(context), parent, false)
            BookItemView(binding)
        } else {
            val binding =
                ItemRecyclerviewPriceBinding.inflate(LayoutInflater.from(context), parent, false)
            PriceItemView(binding)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.e("", "[onBindViewHolder] position : " + position + ", nameList : " + itemList.size)

        if (position % 3 == 0) {
            val offset = if ((position > 0)) position / 3 else 0
            val name = names[offset]


            (viewHolder as BookItemView).bind(name)

            viewHolder.itemView.setOnClickListener {
                if (mOnItemClickListener != null) mOnItemClickListener!!.onItemClick(
                    names[offset], isbn[offset], offset
                )
            }
        } else {
            val offset = if ((position > 0)) position / 3 else 0
            val item = itemList[2 * offset + position % 3 - 1]

            (viewHolder as PriceItemView).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 3 == 0) return VIEW_TYPE.BOOK.ordinal
        return VIEW_TYPE.PRICE.ordinal
    }

    override fun getItemCount(): Int {
        return names.size + itemList.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mOnItemClickListener = listener
    }

    fun updateView(item: PriceItem, position: Int) {
        itemList.removeAt(position)
        itemList.add(position, PriceItem(item))
    }
}