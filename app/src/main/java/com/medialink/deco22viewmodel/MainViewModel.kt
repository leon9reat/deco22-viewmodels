package com.medialink.deco22viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.medialink.deco22viewmodel.models.WeatherItem
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception
import java.text.DecimalFormat

class MainViewModel: ViewModel() {

    private val _listWeather = MutableLiveData<ArrayList<WeatherItem>>().apply { value = arrayListOf() }
    val listWeather : LiveData<ArrayList<WeatherItem>> = _listWeather

    internal fun setWeather(cities: String) {
        val client = AsyncHttpClient()
        val listItem = ArrayList<WeatherItem>()
        val url = "https://api.openweathermap.org/data/2.5/group?id=$cities&units=metric&appid=${BuildConfig.API_KEY}"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responObject = JSONObject(result)
                    val list = responObject.getJSONArray("list")

                    for (i in 0 until list.length()) {
                        val weather = list.getJSONObject(i)

                        val tempInKelvin = weather.getJSONObject("main").getDouble("temp")
                        val tempInCelsius = tempInKelvin - 273

                        val weatherItem = WeatherItem(
                            weather.getInt("id"),
                            weather.getString("name"),
                            weather.getJSONArray("weather").getJSONObject(0).getString("main"),
                            weather.getJSONArray("weather").getJSONObject(0).getString("description"),
                            DecimalFormat("##.##").format(tempInCelsius)
                        )
                        listItem.add(weatherItem)
                    }
                    _listWeather.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
}