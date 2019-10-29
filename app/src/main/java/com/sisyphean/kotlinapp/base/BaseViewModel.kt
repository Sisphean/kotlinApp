package com.sisyphean.kotlinapp.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), LifecycleObserver{

    val mException: MutableLiveData<Throwable> = MutableLiveData()

}