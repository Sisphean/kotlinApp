package com.sisyphean.kotlinapp.model.bean

data class WSMarkets(val data: List<Market>,
                     val changeData: List<Market>,
                     val msgType: Int)