package com.pandexter.recruitmenttask.di

import com.google.gson.Gson
import com.pandexter.recruitmenttask.data.remote.DummyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesClient(
        gson: Gson
    ): DummyService {

        return Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DummyService::class.java)
    }

    private fun getClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()

    companion object {
        const val READ_TIMEOUT = 15L
        const val CONNECT_TIMEOUT = 15L
        const val SERVICE_URL = "google.com"
    }
}