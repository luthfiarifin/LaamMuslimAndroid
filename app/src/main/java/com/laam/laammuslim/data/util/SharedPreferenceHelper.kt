package com.laam.laammuslim.data.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferenceHelper(val context: Context) {

    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getAyahFontSize() = prefs.getString("ayah_text_preference", null)
}

