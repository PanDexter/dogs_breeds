package com.pandexter.recruitmenttask.common.schedulers

import kotlinx.coroutines.CoroutineDispatcher

interface Schedulers {
  fun io(): CoroutineDispatcher
  fun background(): CoroutineDispatcher
  fun main(): CoroutineDispatcher
}
