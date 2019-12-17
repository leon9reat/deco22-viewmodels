package com.medialink.deco22viewmodel

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WeatherAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        adapter = WeatherAdapter()

        rv_main.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        rv_main.adapter = adapter

        btn_city.setOnClickListener {
            val city = ed_id_city.text.toString()
            if (city.isEmpty()) return@setOnClickListener

            mainViewModel.setWeather(city)
            showLoading(true)
        }

        mainViewModel.listWeather.observe(this, Observer { weatherItem ->
            if (weatherItem != null) {
                adapter.setData(weatherItem)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_main.visibility = View.VISIBLE
        } else {
            progress_main.visibility = View.GONE
        }
    }
}
