package com.pandexter.dogsbreed.data.remote.model

import com.google.gson.annotations.SerializedName

data class BreedsDto(@SerializedName("message") val data: Map<String, List<String>>)