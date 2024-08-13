package com.myjb.dev.model.remote.dto

import com.myjb.dev.model.data.Company

data class BookInfoItem(
    val isbn: String,
    val company: Company,
    val image: String,
    val name: String,
    val price: PriceItem,
)