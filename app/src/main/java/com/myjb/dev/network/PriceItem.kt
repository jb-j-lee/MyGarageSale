package com.myjb.dev.network

data class PriceItem(
    var price: String,
    var best: String,
    var better: String,
    var good: String,
) {
    constructor(price: Array<String>) : this(
        price = price[0],
        best = price[1],
        better = price[2],
        good = price[3]
    )

    constructor(item: PriceItem) : this(
        price = item.price,
        best = item.best,
        better = item.better,
        good = item.good
    )
}