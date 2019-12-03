package com.sisyphean.kotlinapp.model.bean

import com.google.gson.annotations.SerializedName

data class Market(var change: String,
                  @SerializedName("change_rate")
                  var changeRate: String,
                  @SerializedName("latest_price")
                  var latestPrice: String,
                  @SerializedName("latest_rmb_price")
                  var latestRmbPrice: String,
                  @SerializedName("max_price_24h")
                  var maxPrice24h: String,
                  @SerializedName("min_price_24h")
                  var minPrice24h: String,
                  @SerializedName("trade_pairs_id")
                  var tradePairsId: Int,
                  @SerializedName("trade_pairs_name")
                  var tradePairsName: String)