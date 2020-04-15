package com.laam.laammuslim.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.laam.laammuslim.data.model.ScheduleOfPray
import com.laam.laammuslim.data.model.TodayWeather

data class MuslimSalatDailyResponse(
    val address: String,
    val city: String,
    val country: String,
    val country_code: String,
    @SerializedName("items")
    val scheduleOfPrays: List<ScheduleOfPray>,
    val postal_code: String,
    val state: String,
    val status_code: Int,
    @SerializedName("today_weather")
    val todayWeather: TodayWeather
)