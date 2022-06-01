package com.pandexter.recruitmenttask.domain.usecase

import com.pandexter.recruitmenttask.common.usecase.NoParametersUseCase
import com.pandexter.recruitmenttask.domain.repository.DummyRepository
import javax.inject.Inject

class DummyUseCase @Inject constructor(private val repository: DummyRepository) :
    NoParametersUseCase<Unit>() {

    override suspend fun run(): Result<Unit> {
        TODO("Not yet implemented")
    }
}