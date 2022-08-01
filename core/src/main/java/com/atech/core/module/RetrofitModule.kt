package com.atech.core.module

import com.atech.core.api.NewsApi
import com.atech.core.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory = GsonConverterFactory.create()


    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun getApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)
}