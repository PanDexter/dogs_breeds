package com.pandexter.dogsbreed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pandexter.dogsbreed.common.ListConverter
import com.pandexter.dogsbreed.data.local.dao.BreedsDao
import com.pandexter.dogsbreed.data.local.dao.FavouriteBreedPhotosDao
import com.pandexter.dogsbreed.data.local.entity.BreedEntity
import com.pandexter.dogsbreed.data.local.entity.FavouritePhotoEntity

@Database(entities = [BreedEntity::class, FavouritePhotoEntity::class], version = 3)
@TypeConverters(ListConverter::class)
abstract class DogDatabase : RoomDatabase() {
    abstract fun breedsDao(): BreedsDao
    abstract fun favouritePhotoDao(): FavouriteBreedPhotosDao
}