package com.laam.laammuslim.di.app

import android.app.Application
import androidx.room.Room
import com.laam.laammuslim.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application) = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "FebrinoStoreDatabase.db"
    )
        .allowMainThreadQueries()
        .createFromAsset("database/myQuran.db")
        .build()
}