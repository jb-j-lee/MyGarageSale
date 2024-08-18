package com.myjb.dev.model.remote.dto

data class PriceItem(
    val price: String,
    val best: String,
    val better: String,
    val good: String,
    val uniform: String,
) {
    constructor(price: Array<String>) : this(
        price = price[0],
        best = price[1],
        better = price[2],
        good = price[3],
        uniform = ""
    )

    constructor(price: String, uniform: String) : this(
        price = price,
        best = "",
        better = "",
        good = "",
        uniform = uniform
    )
}