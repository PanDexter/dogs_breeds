package com.pandexter.dogsbreed.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pandexter.dogsbreed.data.local.entity.FavouritePhotoEntity

@Dao
interface FavouriteBreedPhotosDao {

    @Query("SELECT * FROM favouritePhoto")
    fun getAllPhotos(): List<FavouritePhotoEntity>

    @Query("SELECT * FROM favouritePhoto WHERE breedName=:breed")
    fun getPhotosByBreed(breed: String): List<FavouritePhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: FavouritePhotoEntity)

    @Query("DELETE FROM favouritePhoto WHERE favouritePhoto.photoUrl = :url")
    fun delete(url: String)
}