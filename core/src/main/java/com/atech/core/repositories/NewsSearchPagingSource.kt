package com.atech.core.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.atech.core.api.NewsApi
import com.atech.core.models.Article
import retrofit2.HttpException
import java.io.IOException

class NewsSearchPagingSource(
    private val query: String,
    private val newsApi: NewsApi,
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: 1

        return try {
            val response = newsApi.getSearchForNews(
                query,
                pageNumber = position,
                pageSize = params.loadSize
            )
            LoadResult.Page(
                data = response.articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.articles.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}