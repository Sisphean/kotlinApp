package com.sisyphean.kotlinapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseVMFragment<VM : BaseViewModel> : Fragment() {

    lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        initView()
        subscribe()
    }

    open fun subscribe() {
        mViewModel.mException.observe(this, Observer {
            it?.let { onError(it) }
        })
    }

    open fun onError(it: Throwable) {}

    abstract fun initView()

    private fun initVM() {
        getVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun getVMClass(): Class<VM>? = null

    abstract fun setLayoutId(): Int

}