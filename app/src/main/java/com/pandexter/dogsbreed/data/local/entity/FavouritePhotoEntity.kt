package com.pandexter.dogsbreed.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favouritePhoto",
    indices = [
        Index(value = ["photoUrl"], unique = true)
    ]
)
data class FavouritePhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val photoUrl: String,
    val breedName: String
)