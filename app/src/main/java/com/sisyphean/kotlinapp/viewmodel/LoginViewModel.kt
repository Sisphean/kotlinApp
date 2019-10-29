package com.sisyphean.kotlinapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sisyphean.kotlinapp.base.BaseViewModel
import com.sisyphean.kotlinapp.model.bean.LoginBean
import com.sisyphean.kotlinapp.model.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {

    private val repository: LoginRepository = LoginRepository()

    val loginLiveData: MutableLiveData<LoginBean> = MutableLiveData()

    val loginStateLiveData: MutableLiveData<LoginState> = MutableLiveData()

    fun login(account: String, password: String) {

        viewModelScope.launch(Dispatchers.Default) {


            withContext(Dispatchers.Main) { showLoading()}

            delay(2000)

            val loginResponse = repository.login(account, password)

            withContext(Dispatchers.Main) {

                loginLiveData.value = loginResponse.data
            }



        }


    }

    private fun showLoading() {

        emitUiState(true)
    }

    fun emitUiState(isLoading: Boolean = false) {
        loginStateLiveData.value = LoginState(isLoading)
    }

    data class LoginState(val isLoading: Boolean)

}