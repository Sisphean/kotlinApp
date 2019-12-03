package com.sisyphean.kotlinapp.ui.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.model.bean.Market
import java.math.BigDecimal

class HomeAdapter(context: Context?) : BaseAdapter<Market>(context) {

    companion object {
        const val TYPE_HEADER: Int = 1
        const val TYPE_NORMAL: Int = 0
    }

    lateinit var header: View

    fun addHeader(header: View) {
        this.header = header
    }

    override fun convert(holder: BaseViewHolder, data: Market) {
//        holder.setText(R.id.tv_trade_pairs_name, data.tradePairsName)
//        holder.setText(R.id.tv_latest_price, data.latestPrice)
//        holder.setText(R.id.tv_change_rate, data.changeRate)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.itemViewType == TYPE_HEADER) {

        } else {
            val data = mData[position - 1]
            holder.setText(R.id.tv_trade_pairs_name, data.tradePairsName, null)
            holder.setText(R.id.tv_latest_price, data.latestPrice, null)
            val changeRateView = holder.getView<TextView>(R.id.tv_change_rate)
            val changeRate = data.changeRate.toBigDecimal()
            changeRateView.text = "${changeRate.multiply(BigDecimal.valueOf(100).setScale(2, BigDecimal.ROUND_HALF_DOWN))}%"
            changeRateView.setBackgroundColor(ContextCompat.getColor(context!!, if (changeRate >= BigDecimal.ZERO) R.color.text_green else R.color.text_red))
        }

    }

    override fun setItemLayoutId(): Int = R.layout.item_market

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_HEADER -> BaseViewHolder(header)
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return mData.count() + 1
    }

    fun updateData(data: List<Market>?) {
        data?.let {
            for ((index, market) in mData.withIndex()) {
                for (d in it) {
                    if (market.tradePairsId == d.tradePairsId) {
                        market.change = d.change
                        market.changeRate = d.changeRate
                        market.latestPrice = d.latestPrice
                        market.latestRmbPrice = d.latestRmbPrice
                        market.maxPrice24h = d.maxPrice24h
                        market.minPrice24h = d.minPrice24h

//                        notifyItemChanged(index + 1)
                    }
                }
            }
            val sortedData = mData.sortedByDescending { item -> item.changeRate.toBigDecimal() }
            mData.clear()
            mData.addAll(sortedData)

            notifyItemRangeChanged(1, mData.size)
        }
    }


}