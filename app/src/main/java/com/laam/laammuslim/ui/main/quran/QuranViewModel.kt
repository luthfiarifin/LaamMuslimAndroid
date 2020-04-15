package com.laam.laammuslim.ui.main.quran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laam.laammuslim.data.db.AppDatabase
import javax.inject.Inject

class QuranViewModel @Inject constructor(
    val db: AppDatabase
) : ViewModel() {

    fun getSurahAlFatehah() = db.getAyahDao().getAllAyah()

}