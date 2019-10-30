package com.sisyphean.kotlinapp.ui.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(val context: Context?) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>(){

    val mData: ArrayList<T> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(context).inflate(setItemLayoutId(), parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        convert(holder, mData[position])
    }

    abstract fun convert(holder: BaseViewHolder, data: T)

    abstract fun setItemLayoutId(): Int

    fun loadData(data: List<T>?) {
        data?.let {
            if (it.isNotEmpty()) {
                mData.clear()
                mData.addAll(data)
                notifyDataSetChanged()
            }
        }
    }


    class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val mViews: SparseArray<View> = SparseArray()

        fun <T: View> getView(viewId: Int): T {

            var view: View? = mViews.get(viewId)
            if (view == null) {
                view = itemView.findViewById(viewId)
                mViews.put(viewId, view)
            }
            return view as T
        }

        fun setText(viewId: Int, text: String) {
            val tv: TextView = getView(viewId)
            tv.text = text
        }

        fun setImgResource(viewId: Int, resId: Int) {
            val img: ImageView = getView(viewId)
            img.setImageResource(resId)
        }

        fun setOnClickListener(viewId: Int, listener: View.OnClickListener) {
            val view: View = getView(viewId)
            view.setOnClickListener(listener)
        }
    }
}