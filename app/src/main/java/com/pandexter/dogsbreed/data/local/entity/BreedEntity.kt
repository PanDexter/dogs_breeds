package com.pandexter.dogsbreed.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class BreedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val subBreeds: List<String>
)