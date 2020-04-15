package com.laam.laammuslim.data.db.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sura(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @NonNull
    val name: String,
    @NonNull
    val englishName: String,
    @NonNull
    val englishNameTranslation: String,
    @NonNull
    val numberOfAyahs: Int,
    @NonNull
    val revelationType: String
)