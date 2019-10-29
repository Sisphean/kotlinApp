package com.sisyphean.kotlinapp.ui.adapter

import android.content.Context
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.model.bean.Market

class HomeAdapter(context: Context?) : BaseAdapter<Market>(context) {

    override fun convert(holder: BaseViewHolder, data: Market) {
        holder.setText(R.id.tv_trade_pairs_name, data.tradePairsName)
        holder.setText(R.id.tv_latest_price, data.latestPrice)
        holder.setText(R.id.tv_change_rate, data.changeRate)
    }

    override fun setItemLayoutId(): Int = R.layout.item_market

    fun updateData(data: List<Market>?) {
        data?.let {
            for (market in mData) {
                for (d in it) {
                    if (market.tradePairsId == d.tradePairsId) {
                        market.change = d.change
                        market.changeRate = d.changeRate
                        market.latestPrice = d.latestPrice
                        market.latestRmbPrice = d.latestRmbPrice
                        market.maxPrice24h = d.maxPrice24h
                        market.minPrice24h = d.minPrice24h
                    }
                }
            }
            notifyDataSetChanged()
        }
    }


}