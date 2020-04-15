package com.laam.laammuslim.di.app

import com.laam.laammuslim.data.util.MUSLIM_SALAT_URL
import com.laam.laammuslim.di.MuslimSalatRetrofitQualifier
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    @MuslimSalatRetrofitQualifier
    fun provideMuslimSalatRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MUSLIM_SALAT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

}