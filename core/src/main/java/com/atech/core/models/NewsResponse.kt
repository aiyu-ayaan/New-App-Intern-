package com.atech.core.models

import androidx.annotation.Keep
import com.atech.core.models.Article

@Keep
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)