package com.pandexter.dogsbreed.data.remote.model

import com.google.gson.annotations.SerializedName

data class BreedPhotosDto(@SerializedName("message") val photos: List<String>)