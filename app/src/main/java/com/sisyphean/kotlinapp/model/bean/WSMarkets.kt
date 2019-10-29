package com.sisyphean.kotlinapp.model.bean

import com.google.gson.annotations.SerializedName

data class WSMarkets(val data: List<Market>,
                     @SerializedName("change_data")
                     val changeData: List<Market>,
                     @SerializedName("msg_type")
                     val msgType: Int)