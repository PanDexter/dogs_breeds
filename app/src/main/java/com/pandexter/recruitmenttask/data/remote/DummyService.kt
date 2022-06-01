package com.pandexter.recruitmenttask.data.remote

import retrofit2.http.GET

interface DummyService {

    @GET()
    suspend fun getData()
}