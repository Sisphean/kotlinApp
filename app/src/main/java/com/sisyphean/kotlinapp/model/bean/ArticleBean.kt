package com.sisyphean.kotlinapp.model.bean

data class ArticleBean(val articleId: Int,
                       val category: Int,
                       val detail: String,
                       val isTop: Int,
                       val langId: Int,
                       val summary: String,
                       val time: Long,
                       val title: String)