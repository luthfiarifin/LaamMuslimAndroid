package com.laam.laammuslim.di.ui.main

import com.laam.laammuslim.ui.main.home.HomeFragment
import com.laam.laammuslim.ui.main.notifications.NotificationsFragment
import com.laam.laammuslim.ui.main.quran.QuranFragment
import com.laam.laammuslim.ui.main.schedule_prayer.SchedulePrayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeDashboardFragment(): QuranFragment

    @ContributesAndroidInjector()
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector()
    abstract fun contributeNotificationsFragment(): NotificationsFragment

    @ContributesAndroidInjector()
    abstract fun contributeSchedulePrayerFragment(): SchedulePrayerFragment


}