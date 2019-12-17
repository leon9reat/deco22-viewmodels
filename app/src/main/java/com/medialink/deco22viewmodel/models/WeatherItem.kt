package com.medialink.deco22viewmodel.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherItem(
    var id: Int = 0,
    var name: String,
    var currentWeather: String?,
    var description: String?,
    var temperature: String?
) : Parcelable
