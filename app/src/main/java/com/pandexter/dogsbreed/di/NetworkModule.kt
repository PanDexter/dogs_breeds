package com.pandexter.dogsbreed.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pandexter.dogsbreed.data.remote.DogsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun providesClient(
        gson: Gson
    ): DogsService = Retrofit.Builder()
        .baseUrl(SERVICE_URL)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(DogsService::class.java)

    private fun getClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()

    companion object {
        const val READ_TIMEOUT = 15L
        const val CONNECT_TIMEOUT = 15L
        const val SERVICE_URL = "https://dog.ceo/api/"
    }
}