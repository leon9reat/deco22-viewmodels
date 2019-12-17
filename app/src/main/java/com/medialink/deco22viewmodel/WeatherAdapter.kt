package com.medialink.deco22viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medialink.deco22viewmodel.models.WeatherItem
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherAdapter: RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {

    private val mData = arrayListOf<WeatherItem>()

    fun setData(items: ArrayList<WeatherItem>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: WeatherItem) {
            with(itemView) {
                tv_kota.text = item.name
                tv_temp.text = item.temperature
                tv_desc.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mData[position])
    }
}