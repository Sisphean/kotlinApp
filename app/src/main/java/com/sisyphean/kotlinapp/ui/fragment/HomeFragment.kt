package com.sisyphean.kotlinapp.ui.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.base.BaseVMFragment
import com.sisyphean.kotlinapp.model.api.ApiService
import com.sisyphean.kotlinapp.model.bean.ArticleBean
import com.sisyphean.kotlinapp.model.bean.BannerBean
import com.sisyphean.kotlinapp.ui.adapter.HomeAdapter
import com.sisyphean.kotlinapp.ui.view.TextBannerView
import com.sisyphean.kotlinapp.utils.GlideImageLoader
import com.sisyphean.kotlinapp.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeViewModel>() {

    companion object {
        fun getInstance(): Fragment = HomeFragment()
    }

    private val bannerImgs: MutableList<String> = mutableListOf()

    private val articles: MutableList<String> = mutableListOf()

    private lateinit var banner: Banner

    private lateinit var tvBanner: TextBannerView

    private lateinit var viewFlipper: ViewFlipper

    private val homeAdapter by lazy { HomeAdapter(activity) }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun getVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java

    override fun initView() {


        val header = LayoutInflater.from(context).inflate(R.layout.header_market, null, false)
        banner = header.findViewById(R.id.banner_article)

        banner.run {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setImageLoader(GlideImageLoader())
        }

        mViewModel.getBanner()

        tvBanner = header.findViewById(R.id.tv_banner)
        viewFlipper = header.findViewById(R.id.view_flipper)
        mViewModel.getArticles()


        recycler_view.run {
            layoutManager = LinearLayoutManager(activity)
            homeAdapter.addHeader(header)
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

            articleData.observe(this@HomeFragment, Observer {
                it?.let {
                    setArticles(it)
                }
            })
        }
    }

    private fun setArticles(it: List<ArticleBean>) {
        for (article in it) {
            articles.add(article.title)
        }
        tvBanner.setDatas(articles)

        viewFlipper.addView(getTextView("111111111"))
        viewFlipper.addView(getTextView("222222222"))
        viewFlipper.addView(getTextView("333333333"))

        viewFlipper.setInAnimation(activity, R.anim.anim_bottom_in)
        viewFlipper.setOutAnimation(activity, R.anim.anim_top_out)

        viewFlipper.flipInterval = 1000

        viewFlipper.startFlipping()
    }

    fun getTextView(text: String) : TextView{
        val textView = TextView(activity)
        textView.text = text
        return textView
    }

    private fun setBanners(it: List<BannerBean>) {
        for (banner in it) {
            bannerImgs.add(ApiService.BASE_URL + "/" + banner.bannerImg)
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
        tvBanner.startViewAnimator()
    }

    override fun onPause() {
        super.onPause()
        Log.d("xxx", "onPause is called...")
        mViewModel.closeWebSocket()
        banner.stopAutoPlay()
        tvBanner.stopViewAnimator()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("xxx", "onDestroy is called...")
        mViewModel.closeWebSocket()
    }
}