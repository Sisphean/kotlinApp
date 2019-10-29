package com.sisyphean.kotlinapp.model.bean

data class Market(val change: String,
                  val changeRate: String,
                  val latestPrice: String,
                  val latestRmbPrice: String,
                  val maxPrice24h: String,
                  val minPrice24h: String,
                  val tradePairsId: Int,
                  val tradePairsName: String)