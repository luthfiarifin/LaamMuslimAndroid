package com.laam.laammuslim.di.ui.main

import com.laam.laammuslim.data.api.GithubAPI
import com.laam.laammuslim.data.api.MuslimSalatAPI
import com.laam.laammuslim.di.GithubRetrofitQualifier
import com.laam.laammuslim.di.MainScope
import com.laam.laammuslim.di.MuslimSalatRetrofitQualifier
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
object MainModule {

    @MainScope
    @JvmStatic
    @Provides
    fun provideMuslimSalatAPI(@MuslimSalatRetrofitQualifier retrofit: Retrofit): MuslimSalatAPI =
        retrofit.create(MuslimSalatAPI::class.java)

    @MainScope
    @JvmStatic
    @Provides
    fun provideGithubAPI(@GithubRetrofitQualifier retrofit: Retrofit): GithubAPI =
        retrofit.create(GithubAPI::class.java)

    @MainScope
    @JvmStatic
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable =
        CompositeDisposable()
}