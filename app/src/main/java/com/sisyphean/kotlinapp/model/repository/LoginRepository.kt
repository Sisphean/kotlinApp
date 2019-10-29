package com.sisyphean.kotlinapp.model.repository

import com.sisyphean.kotlinapp.model.api.RetrofitClient
import com.sisyphean.kotlinapp.model.bean.Response
import com.sisyphean.kotlinapp.model.bean.LoginBean

class LoginRepository {

    suspend fun login(account: String, password: String) : Response<LoginBean> {
        return RetrofitClient.service.login(account, password)
    }
}