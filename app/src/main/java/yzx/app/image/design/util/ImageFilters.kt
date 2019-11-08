package yzx.app.image.design.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint


object ImageFilters {

    const val totalFilterCount = 20

    fun toWhiteBlack(source: Bitmap, emptyCanvas: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                2.55f, 0f, 0f, 0f, -255f,
                0f, 2.55f, 0f, 0f, -255f,
                0f, 0f, 2.55f, 0f, -255f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(emptyCanvas).apply {
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                colorFilter = filter
                drawBitmap(source, 0f, 0f, this)
            }
        }
    }
}