package com.laam.laammuslim.di.app

import android.app.Application
import com.laam.laammuslim.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RoomDatabaseModule::class,
        ActivityBuilderModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}