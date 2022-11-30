package com.pandexter.dogsbreed.di

import com.pandexter.dogsbreed.data.remote.DogsService
import com.pandexter.dogsbreed.data.repository.DogBreedRepositoryImpl
import com.pandexter.dogsbreed.domain.repository.DogBreedRepository
import com.pandexter.dogsbreed.domain.usecase.GetAllBreedsUseCase
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
    fun provideRepository(service: DogsService): DogBreedRepository = DogBreedRepositoryImpl(service)

    @Provides
    fun provideUseCase(repository: DogBreedRepository): GetAllBreedsUseCase = GetAllBreedsUseCase(repository)
}
