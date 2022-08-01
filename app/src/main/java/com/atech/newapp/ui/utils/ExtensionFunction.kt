package com.atech.newapp.ui.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import com.atech.core.utils.Constants.Companion.DEFAULT_CORNER_RADIUS
import com.atech.newapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.google.android.material.color.MaterialColors
import java.text.SimpleDateFormat
import java.util.*

fun String?.loadImage(
    parentView: View,
    view: ImageView,
    progressBar: ProgressBar? = null,
    cornerRadius: Int = DEFAULT_CORNER_RADIUS,
) =
    Glide.with(parentView)
        .load(this ?: "")
        .fitCenter()
        .error(R.drawable.no_image)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = View.GONE
                return false
            }

        })
        .apply(com.bumptech.glide.request.RequestOptions.bitmapTransform(RoundedCorners(cornerRadius)))
        .timeout(10000)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)


fun Context.openCustomChromeTab(link: String) = this.run {
    val defaultColors = CustomTabColorSchemeParams.Builder()
        .setToolbarColor(
            MaterialColors.getColor(
                this,
                androidx.appcompat.R.attr.colorAccent,
                Color.RED
            )
        )
        .build()
    val customTabIntent =
        CustomTabsIntent.Builder().setDefaultColorSchemeParams(defaultColors).build()
    customTabIntent.intent.`package` = "com.android.chrome"
    customTabIntent.launchUrl(this, Uri.parse(link))
}

fun Activity.openShareDeepLink(title: String, link: String) =
    this.startActivity(Intent.createChooser(Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT, """
            $title .
            Link: $link
        """.trimIndent()
        )
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, title)

        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }, null))


@SuppressLint("SimpleDateFormat")
fun Date.convertDateToTime(): String =
    SimpleDateFormat("dd/MM/yyyy").format(this)
