package com.pandexter.dogsbreed.ui.favouritePhotos.model

import com.pandexter.dogsbreed.common.capitalizeFirstLetter

sealed class BreedSelector(open val name: String) {
    object All : BreedSelector("All")
    data class Breed(override val name: String) : BreedSelector(name) {
        override fun toString(): String = name.capitalizeFirstLetter()
    }

    override fun toString(): String {
        return name
    }
}