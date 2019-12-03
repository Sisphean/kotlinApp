package com.sisyphean.kotlinapp.ui.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.model.bean.Market

class AutoPollAdapter(context: Context?) : BaseAdapter<Market>(context) {

    override fun convert(holder: BaseViewHolder, data: Market) {
        holder.setText(R.id.tv_trade_pairs_name, data.tradePairsName, null)
        val priceColor = if (data.changeRate.toFloat() <= 0) ContextCompat.getColor(context!!, R.color.text_red) else ContextCompat.getColor(context!!, R.color.text_green)
        holder.setText(R.id.tv_trade_pairs_price, data.latestPrice, priceColor)
        holder.setText(R.id.tv_change_rate, (data.changeRate.toFloat() * 100).toString() + "%", priceColor)
        val rmbPrice = data.latestRmbPrice
        holder.setText(R.id.tv_trade_pairs_rmb, "≈${rmbPrice}CNY", null)
    }

    override fun setItemLayoutId(): Int = R.layout.item_auto_poll

    override fun getItemCount(): Int = Integer.MAX_VALUE

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        if (mData.count() != 0)
        super.onBindViewHolder(holder, position % mData.count())
    }

}