package com.atech.core.api

import com.atech.core.models.NewsResponse
import com.atech.core.utils.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getHeadLines(
        @Query("country") countryCode: String = "in",
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

}