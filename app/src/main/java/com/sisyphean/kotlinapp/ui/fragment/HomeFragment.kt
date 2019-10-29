package com.sisyphean.kotlinapp.ui.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.base.BaseVMFragment
import com.sisyphean.kotlinapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    companion object{
        fun getInstance() : Fragment = HomeFragment()
    }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun getVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java

    override fun initView() {

    }

    override fun subscribe() {
        super.subscribe()
        mViewModel.apply {
            msgData.observe(this@HomeFragment, Observer {
                tv_msg.text = it.msgType.toString()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("xxx", "onResume is called...")
        mViewModel.getTicker()
    }

    override fun onPause() {
        super.onPause()
        Log.d("xxx", "onPause is called...")
        mViewModel.closeWebSocket()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("xxx", "onDestroy is called...")
        mViewModel.closeWebSocket()
    }
}