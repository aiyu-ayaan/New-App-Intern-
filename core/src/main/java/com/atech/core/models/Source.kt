package com.atech.core.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Source(
    val id: String?,
    val name: String?
) : Parcelable