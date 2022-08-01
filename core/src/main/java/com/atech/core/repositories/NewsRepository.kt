package com.atech.core.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.atech.core.api.NewsApi
import javax.inject.Inject


class NewsRepository @Inject constructor(
    private val api: NewsApi
) {

    fun getTopHeadingResult(countryCode: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsTopHeadingPagingSource(countryCode, api) }
        ).liveData

}