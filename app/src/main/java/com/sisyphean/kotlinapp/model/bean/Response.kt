package com.sisyphean.kotlinapp.model.bean

data class Response<T>(val status: Int,
                       val msg: String,
                       val data: T)