package com.pandexter.recruitmenttask.di

import com.pandexter.recruitmenttask.data.remote.DummyService
import com.pandexter.recruitmenttask.data.repository.DummyRepositoryImpl
import com.pandexter.recruitmenttask.domain.repository.DummyRepository
import com.pandexter.recruitmenttask.domain.usecase.DummyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeatureModule {

    @Provides
    @Singleton
    fun provideRepository(service: DummyService): DummyRepository = DummyRepositoryImpl(service)

    @Provides
    fun provideUseCase(repository: DummyRepository): DummyUseCase = DummyUseCase(repository)
}
