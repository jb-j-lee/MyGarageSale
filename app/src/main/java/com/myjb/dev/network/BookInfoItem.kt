package com.myjb.dev.network

data class BookInfoItem(
    var isbn: String,
    var company: Int,
    var image: String,
    var name: String,
    var price: PriceItem,
)