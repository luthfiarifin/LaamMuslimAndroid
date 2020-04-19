package com.laam.laammuslim.ui.main.quran_ayah

import androidx.lifecycle.ViewModel
import com.laam.laammuslim.data.db.AppDatabase
import com.laam.laammuslim.data.util.SharedPreferenceHelper
import javax.inject.Inject

class QuranAyahViewModel @Inject constructor(
    val db: AppDatabase,
    val pref: SharedPreferenceHelper
) : ViewModel() {

    fun getAyahBySura(id: Int) = db.getAyahDao().getAllAyah(id)

    fun getFontSize() = pref.getAyahFontSize()
}