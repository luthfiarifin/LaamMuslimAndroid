package com.laam.laammuslim.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.laam.laammuslim.data.db.dao.AyahDao
import com.laam.laammuslim.data.db.entities.Ayah
import com.laam.laammuslim.data.db.entities.Sura

@Database(
    entities = [Sura::class, Ayah::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAyahDao(): AyahDao
}