package com.laam.laammuslim.data.listener

import android.view.View
import com.laam.laammuslim.data.db.entities.Sura

interface SurahRecyclerListener {

    fun onClick(view: View, sura: Sura)
}