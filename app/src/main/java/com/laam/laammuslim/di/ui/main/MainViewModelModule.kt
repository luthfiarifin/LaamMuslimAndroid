package com.laam.laammuslim.di.ui.main

import androidx.lifecycle.ViewModel
import com.laam.laammuslim.di.viewmodel.ViewModelKey
import com.laam.laammuslim.ui.main.home.HomeViewModel
import com.laam.laammuslim.ui.main.quran_ayah.QuranAyahViewModel
import com.laam.laammuslim.ui.main.quran_sura.QuranSuraViewModel
import com.laam.laammuslim.ui.main.schedule_prayer.SchedulePrayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuranSuraViewModel::class)
    abstract fun bindQuranSuraViewModel(quranViewModel: QuranSuraViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuranAyahViewModel::class)
    abstract fun bindQuranAyahViewModel(quranAyahViewModel: QuranAyahViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SchedulePrayerViewModel::class)
    abstract fun bindSchedulePrayerViewModel(schedulePrayerViewModel: SchedulePrayerViewModel): ViewModel

}