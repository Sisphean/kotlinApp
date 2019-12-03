package com.sisyphean.kotlinapp.ui.fragment

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.base.BaseVMFragment
import com.sisyphean.kotlinapp.model.api.ApiService
import com.sisyphean.kotlinapp.model.bean.ArticleBean
import com.sisyphean.kotlinapp.model.bean.BannerBean
import com.sisyphean.kotlinapp.ui.adapter.AutoPollAdapter
import com.sisyphean.kotlinapp.ui.adapter.HomeAdapter
import com.sisyphean.kotlinapp.ui.view.AutoPollRecyclerView
import com.sisyphean.kotlinapp.utils.GlideImageLoader
import com.sisyphean.kotlinapp.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeViewModel>(), OnRefreshListener {

    companion object {
        fun getInstance(): Fragment = HomeFragment()
    }

    private val bannerImgs: MutableList<String> = mutableListOf()

    private val articles: MutableList<String> = mutableListOf()

    private lateinit var banner: Banner

    private lateinit var viewFlipper: ViewFlipper

    private lateinit var autoPollRecyclerView: AutoPollRecyclerView

    private val homeAdapter by lazy { HomeAdapter(activity) }

    private val autoPollAdapter by lazy { AutoPollAdapter(activity) }

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

        viewFlipper = header.findViewById(R.id.view_flipper)
        mViewModel.getArticles()

        autoPollRecyclerView = header.findViewById(R.id.auto_poll_view)
        autoPollRecyclerView.run {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = autoPollAdapter
        }

        recycler_view.run {
            layoutManager = LinearLayoutManager(activity)
            homeAdapter.addHeader(header)
            adapter = homeAdapter
        }

        refresh_layout.setOnRefreshListener(this)
        refresh_layout.setEnableLoadMore(false)

    }

    override fun subscribe() {
        super.subscribe()
        mViewModel.apply {
            msgData.observe(this@HomeFragment, Observer {
                homeAdapter.loadData(it)
                autoPollAdapter.loadData(it)
                autoPollRecyclerView.startPoll()
                refresh_layout.finishRefresh()
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

        fun getTextView(text: String): TextView {
            val textView = TextView(activity)
            textView.run {
                this.text = text
                gravity = Gravity.CENTER_VERTICAL
                setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notice, 0, 0, 0)
            }
            return textView
        }

        for (article in it) {
            viewFlipper.addView(getTextView(article.title))
        }

        viewFlipper.setInAnimation(activity, R.anim.anim_bottom_in)
        viewFlipper.setOutAnimation(activity, R.anim.anim_top_out)

        viewFlipper.flipInterval = 2000

        viewFlipper.startFlipping()
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

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.closeWebSocket()
        mViewModel.getMarkets()
    }
}