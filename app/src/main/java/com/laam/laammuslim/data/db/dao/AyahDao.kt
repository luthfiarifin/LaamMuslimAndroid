package com.laam.laammuslim.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.laam.laammuslim.data.db.entities.Ayah

@Dao
interface AyahDao {

    @Query("SELECT * FROM ayah WHERE suraID = 1")
    fun getAllAyah(): LiveData<List<Ayah>>
}