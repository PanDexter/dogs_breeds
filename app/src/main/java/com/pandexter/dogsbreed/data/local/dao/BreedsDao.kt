package com.pandexter.dogsbreed.data.local.dao

import androidx.room.*
import com.pandexter.dogsbreed.data.local.entity.BreedEntity

@Dao
interface BreedsDao {

    @Transaction
    @Query("SELECT * FROM breed")
    fun getAllBreeds(): List<BreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(breeds: List<BreedEntity>)

}