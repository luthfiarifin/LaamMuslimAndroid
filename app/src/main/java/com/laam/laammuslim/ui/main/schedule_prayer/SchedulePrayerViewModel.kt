package com.laam.laammuslim.ui.main.schedule_prayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laam.laammuslim.data.api.MuslimSalatAPI
import com.laam.laammuslim.data.model.MuslimSalatDailyResponse
import com.laam.laammuslim.data.model.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulePrayerViewModel @Inject constructor(
    val api: MuslimSalatAPI,
    val disposable: CompositeDisposable
) : ViewModel() {
    fun getDailySchedule(city: String, date: String): LiveData<Status<MuslimSalatDailyResponse>> {
        val result: MutableLiveData<Status<MuslimSalatDailyResponse>> = MutableLiveData()

        result.postValue(Status.loading())
        disposable.add(
            api.getScheduleSalatDailyByDate(city, date)
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


    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}
