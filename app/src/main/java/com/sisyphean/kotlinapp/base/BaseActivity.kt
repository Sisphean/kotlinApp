package com.sisyphean.kotlinapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity: AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())

        initView()
    }

    abstract fun initView()

    abstract fun setLayoutId(): Int

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}