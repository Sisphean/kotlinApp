package com.sisyphean.kotlinapp.model.bean

data class UserBean(val uid: Int,
                    val mobile: String,
                    val email: String,
                    val username: String,
                    val password: String,
                    val regDate: String,
                    val status: String)