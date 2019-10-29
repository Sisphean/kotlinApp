package com.sisyphean.kotlinapp.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity(), LifecycleObserver {

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        initVM()
        subscribe()
        initView()
    }

    abstract fun initView()

    abstract fun setLayoutId(): Int

    private fun initVM() {

        /*
        val getVMClass = getVMClass()
        if (getVMClass != null) {
            mViewModel = ViewModelProviders.of(this).get(getVMClass)
            lifecycle.addObserver(mViewModel)
        }
        */

        getVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            mViewModel.let {
                lifecycle.addObserver(it)
            }
        }

    }

    open fun subscribe() {
        /*AppCompatActivity是LifecycleOwner的实现类
                    Observer是java的一个函数式接口，可以利用SAM装换
                 */
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(it: Throwable) {}

    abstract fun getVMClass(): Class<VM>?

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.let(lifecycle::removeObserver)
    }

    protected fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

