package com.laam.laammuslim.ui.main.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laam.laammuslim.data.api.GithubAPI
import com.laam.laammuslim.data.model.GithubProfileResponse
import com.laam.laammuslim.data.model.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AboutViewModel @Inject constructor(
    val api: GithubAPI,
    val disposable: CompositeDisposable
) : ViewModel() {

    fun getUserProfile(): LiveData<Status<GithubProfileResponse>> {
        val result: MutableLiveData<Status<GithubProfileResponse>> = MutableLiveData()

        result.postValue(Status.loading())
        disposable.add(
            api.getUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onError = {
                        result.postValue(Status.error(it.message.toString(), null))
                    },
                    onSuccess = {
                        it?.let { response ->
                            result.postValue(Status.success(response))
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