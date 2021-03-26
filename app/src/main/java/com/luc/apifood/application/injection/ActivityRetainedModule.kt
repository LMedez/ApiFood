package com.luc.apifood.application.injection

import com.luc.apifood.domain.ApiFoodRepository
import com.luc.apifood.domain.ApiFoodRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @ExperimentalCoroutinesApi
    @Binds
    abstract fun dataSource(impl: ApiFoodRepositoryImpl): ApiFoodRepository
}