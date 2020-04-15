package com.laam.laammuslim.di

import javax.inject.Scope

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class MainScope()

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class HomeScope()

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ScheduleScope()
