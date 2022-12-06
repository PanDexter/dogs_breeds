package com.pandexter.dogsbreed.common

inline fun <T, R> T.runRecoverCatching(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}