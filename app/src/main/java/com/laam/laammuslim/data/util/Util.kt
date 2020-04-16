package com.laam.laammuslim.data.util

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import java.text.SimpleDateFormat
import java.util.*

const val MUSLIM_SALAT_URL = "http://muslimsalat.com/"


fun View.changeNavigation(direction: NavDirections) {
    Navigation.findNavController(this).navigate(direction)
}

// Date Format
@SuppressLint("SimpleDateFormat")
private val m24HourSDF = SimpleDateFormat("HH:mm")

@SuppressLint("SimpleDateFormat")
private val m12HourSDF = SimpleDateFormat("hh:mm aa")

@SuppressLint("SimpleDateFormat")
fun convertTo24HrFormat(date: String): String = m24HourSDF.format(m12HourSDF.parse(date)!!)

fun convertToDateFormat(date: String) = m24HourSDF.parse(date)!!

@SuppressLint("SimpleDateFormat")
val getCurrentTimeFormat: String = SimpleDateFormat("hh:mm aa").format(Date())

@SuppressLint("SimpleDateFormat")
val getCurrentDayFormat: String = SimpleDateFormat("EEEE").format(Date())

@SuppressLint("SimpleDateFormat")
val getCurrentDateNormalFormat: String = SimpleDateFormat("dd MMMM yyyy").format(Date())

@SuppressLint("SimpleDateFormat")
fun getDayFormat(date: Date): String = SimpleDateFormat("EEEE").format(date)

@SuppressLint("SimpleDateFormat")
fun getDateNormalFormat(date: Date): String = SimpleDateFormat("dd MMMM yyyy").format(date)

@SuppressLint("SimpleDateFormat")
fun getDateFormat(date: Date): String = SimpleDateFormat("yyyy-MM-dd").format(date)

fun removeColon(string: String) = string.filterNot { it == ':' }.toInt()

fun convertFromLongToTime(mills: Long): String {
    val hours = (mills / (1000 * 60 * 60)).toString()
    val minutes = ((mills / (1000 * 60)) % 60).toString()
    val seconds = ((mills / 1000) % 60 % 60).toString()

    return "${(if (hours.length == 1) "0$hours" else hours)}:${(if (minutes.length == 1) "0$minutes" else minutes)}:${(if (seconds.length == 1) "0$seconds" else seconds)}"
}

fun getDifferentMillsTime(time: String): Long =
    convertToDateFormat(time).time - convertToDateFormat(convertTo24HrFormat(getCurrentTimeFormat)).time

fun getDifferentMillsTimeBA(before: String, after: String): Long =
    convertToDateFormat(after).time - convertToDateFormat(before).time