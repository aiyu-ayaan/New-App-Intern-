package com.atech.core.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(
    tableName = "article"
)
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @Embedded val source: Source?,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val url: String?,
    val urlToImage: String?
) : Parcelable