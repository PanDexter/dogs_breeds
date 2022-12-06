package com.pandexter.dogsbreed.common

import java.util.*

fun String.capitalizeFirstLetter(): String =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }