package com.laam.laammuslim.ui.main.home

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laam.laammuslim.data.api.MuslimSalatAPI
import com.laam.laammuslim.data.db.AppDatabase
import com.laam.laammuslim.data.model.MuslimSalatDailyResponse
import com.laam.laammuslim.data.model.Status
import com.laam.laammuslim.data.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val db: AppDatabase,
    val api: MuslimSalatAPI,
    val disposable: CompositeDisposable
) : ViewModel() {

    fun getDailySchedule(city: String): LiveData<Status<MuslimSalatDailyResponse>> {
        val result: MutableLiveData<Status<MuslimSalatDailyResponse>> = MutableLiveData()

        result.postValue(Status.loading())
        disposable.add(
            api.getScheduleSalatDaily(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
                        result.postValue(Status.error(it.message.toString(), null))
                    },
                    onSuccess = {
                        it?.let { muslimSalatDailyResponse ->
                            result.postValue(Status.success(muslimSalatDailyResponse))
                            return@subscribeBy
                        }
                        result.postValue(Status.error("Empty Data", null))
                    }
                )
        )

        return result
    }

    val liveDataTimePray: MutableLiveData<List<String>> = MutableLiveData()
    fun getTimePray(data: MuslimSalatDailyResponse): LiveData<List<String>> {
        val list = mutableListOf<String>()

        val timeStampNow = convertTo24HrFormat(getCurrentTimeFormat)
        val subuh = convertTo24HrFormat(data.scheduleOfPrays[0].fajr)
        val shurooq = convertTo24HrFormat(data.scheduleOfPrays[0].shurooq)
        val zuhur = convertTo24HrFormat(data.scheduleOfPrays[0].dhuhr)
        val ashar = convertTo24HrFormat(data.scheduleOfPrays[0].asr)
        val maghrib = convertTo24HrFormat(data.scheduleOfPrays[0].maghrib)
        val isya = convertTo24HrFormat(data.scheduleOfPrays[0].isha)

        var next = ""
        var mills: Long = 0

        when (removeColon(timeStampNow)) {
            in removeColon(subuh)..removeColon(shurooq) -> {
                next = "Shurooq $shurooq"
                mills = getDifferentMillsTime(shurooq)
            }
            in removeColon(shurooq)..removeColon(zuhur) -> {
                next = "Zuhur $zuhur"
                mills = getDifferentMillsTime(zuhur)
            }
            in removeColon(zuhur)..removeColon(ashar) -> {
                next = "Ashar $ashar"
                mills = getDifferentMillsTime(ashar)
            }
            in removeColon(ashar)..removeColon(maghrib) -> {
                next = "Maghrib $maghrib"
                mills = getDifferentMillsTime(maghrib)
            }
            in removeColon(maghrib)..removeColon(isya) -> {
                next = "Isya $isya"
                mills = getDifferentMillsTime(isya)
            }
            in (removeColon(isya)..2359) -> {
                next = "Subuh $subuh"
                mills = getDifferentMillsTime("23:59") + getDifferentMillsTimeBA("00:00", subuh)
            }
            in (0..removeColon(subuh)) -> {
                next = "Subuh $subuh"
                mills = getDifferentMillsTime(subuh)
            }
        }
        list.add(0, next)
        object : CountDownTimer(mills, 1000) {
            override fun onFinish() {}

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                list.add(1, "${convertFromLongToTime(millisUntilFinished)} Lagi")
                liveDataTimePray.postValue(list)
            }
        }.start()

        return liveDataTimePray
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}