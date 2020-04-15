package com.laam.laammuslim.di.app

import com.laam.laammuslim.di.MainScope
import com.laam.laammuslim.di.ui.main.MainFragmentBuildersModule
import com.laam.laammuslim.di.ui.main.MainModule
import com.laam.laammuslim.di.ui.main.MainViewModelModule
import com.laam.laammuslim.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            MainModule::class,
            MainViewModelModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

}