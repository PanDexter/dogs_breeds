package com.pandexter.recruitmenttask.data.repository

import com.pandexter.recruitmenttask.data.remote.DummyService
import com.pandexter.recruitmenttask.domain.repository.DummyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyRepositoryImpl @Inject constructor(
    private val service: DummyService
) : DummyRepository {

}