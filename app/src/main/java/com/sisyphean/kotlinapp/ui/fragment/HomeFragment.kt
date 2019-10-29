package com.sisyphean.kotlinapp.ui.fragment

import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.base.BaseVMFragment
import com.sisyphean.kotlinapp.model.api.ApiService
import com.sisyphean.kotlinapp.model.bean.BannerBean
import com.sisyphean.kotlinapp.ui.adapter.HomeAdapter
import com.sisyphean.kotlinapp.utils.GlideImageLoader
import com.sisyphean.kotlinapp.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    companion object{
        fun getInstance() : Fragment = HomeFragment()
    }

    private val bannerImgs: MutableList<String> = mutableListOf()

    private lateinit var banner: Banner

    private val homeAdapter by lazy { HomeAdapter(activity) }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun getVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java

    override fun initView() {


        val header = ViewGroup.inflate(activity, R.layout.header_market, null)
        banner = header.findViewById(R.id.header_market)

        banner.run {
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            setImageLoader(GlideImageLoader())
        }

        mViewModel.getBanner()


        recycler_view.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }

    }

    override fun subscribe() {
        super.subscribe()
        mViewModel.apply {
            msgData.observe(this@HomeFragment, Observer {
                homeAdapter.loadData(it)
            })

            msgChangeData.observe(this@HomeFragment, Observer {
                homeAdapter.updateData(it)
            })

            bannerData.observe(this@HomeFragment, Observer {
                it?.let {
                    setBanners(it)
                }
            })
        }
    }

    private fun setBanners(it: List<BannerBean>) {
        for (banner in it) {
            bannerImgs.add(ApiService.BASE_URL + banner.bannerImg)
        }
        banner.setImages(bannerImgs)
                .setDelayTime(3000)
                .start()
    }

    override fun onResume() {
        super.onResume()
        Log.d("xxx", "onResume is called...")
        mViewModel.getMarkets()
        banner.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        Log.d("xxx", "onPause is called...")
        mViewModel.closeWebSocket()
        banner.stopAutoPlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("xxx", "onDestroy is called...")
        mViewModel.closeWebSocket()
    }
}