package com.pandexter.dogsbreed.di

import android.content.Context
import androidx.room.Room
import com.pandexter.dogsbreed.data.local.DogDatabase
import com.pandexter.dogsbreed.data.local.dao.BreedsDao
import com.pandexter.dogsbreed.data.local.dao.FavouriteBreedPhotosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideBreedsDao(dogDatabase: DogDatabase): BreedsDao = dogDatabase.breedsDao()

    @Provides
    fun provideFavouriteBreedPhotoDao(dogDatabase: DogDatabase): FavouriteBreedPhotosDao =
        dogDatabase.favouritePhotoDao()

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): DogDatabase {
        return Room.databaseBuilder(appContext, DogDatabase::class.java, "Dog")
            .fallbackToDestructiveMigration()
            .build()
    }

}