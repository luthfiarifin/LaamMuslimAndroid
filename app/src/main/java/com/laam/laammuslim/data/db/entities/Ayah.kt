package com.laam.laammuslim.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Sura::class,
        parentColumns = ["id"],
        childColumns = ["suraID"]
    )]
)

data class Ayah(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val suraID: Int,
    val verseID: Int,
    val ayahText: String,
    val indoText: String,
    val readText: String
)