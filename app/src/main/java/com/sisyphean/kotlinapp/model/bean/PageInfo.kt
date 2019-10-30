package com.sisyphean.kotlinapp.model.bean

data class PageInfo<T>(val total: Int,
                       val size: Int,
                       val pageNum: Int,
                       val nextPage: Int,
                       val prePage: Int,
                       val pages: Int,
                       val list: List<T>)