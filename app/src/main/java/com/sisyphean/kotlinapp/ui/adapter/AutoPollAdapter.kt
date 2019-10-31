package com.sisyphean.kotlinapp.ui.adapter

import android.content.Context
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.model.bean.Market

class AutoPollAdapter(context: Context?) : BaseAdapter<Market>(context) {

    override fun convert(holder: BaseViewHolder, data: Market) {
        holder.setText(R.id.tv_trade_pairs_name, data.tradePairsName)
    }

    override fun setItemLayoutId(): Int = R.layout.item_auto_poll

    override fun getItemCount(): Int = Integer.MAX_VALUE

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        if (mData.count() != 0)
        super.onBindViewHolder(holder, position % mData.count())
    }

}