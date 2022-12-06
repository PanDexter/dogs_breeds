package com.pandexter.dogsbreed.di

import com.pandexter.dogsbreed.data.local.dao.BreedsDao
import com.pandexter.dogsbreed.data.local.dao.FavouriteBreedPhotosDao
import com.pandexter.dogsbreed.data.remote.DogsService
import com.pandexter.dogsbreed.data.repository.DogRepositoryImpl
import com.pandexter.dogsbreed.domain.repository.DogRepository
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
    fun provideRepository(
        service: DogsService,
        favouriteBreedPhotosDao: FavouriteBreedPhotosDao,
        breedsDao: BreedsDao
    ): DogRepository = DogRepositoryImpl(service, favouriteBreedPhotosDao, breedsDao)

    @Provides
    fun provideUseCase(repository: DogRepository): GetAllBreedsUseCase =
        GetAllBreedsUseCase(repository)
}
