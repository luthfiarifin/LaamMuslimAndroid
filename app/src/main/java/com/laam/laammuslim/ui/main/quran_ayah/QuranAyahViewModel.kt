package com.laam.laammuslim.ui.main.quran_ayah

import androidx.lifecycle.ViewModel
import com.laam.laammuslim.data.db.AppDatabase
import javax.inject.Inject

class QuranAyahViewModel @Inject constructor(
    val db: AppDatabase
) : ViewModel() {

    fun getAyahBySura(id: Int) = db.getAyahDao().getAllAyah(id)
}