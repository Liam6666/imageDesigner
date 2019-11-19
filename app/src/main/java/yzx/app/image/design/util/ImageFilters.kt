package yzx.app.image.design.util

import android.graphics.*
import yzx.app.image.design.utils.runCacheOutOfMemory
import java.util.*
import kotlin.collections.ArrayList


interface FilterEntity {
    fun todo(source: Bitmap): Bitmap
    fun name(): String
}


object ImageFilters {

    private val emptyBitmaps = WeakHashMap<Bitmap, Bitmap>()


    fun makeEmptyBitmap(source: Bitmap, onMemoryError: () -> Unit) {
        runCacheOutOfMemory({
            val empty = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.RGB_565)
            emptyBitmaps[source] = empty
        }) { onMemoryError.invoke() }
    }

    fun getResult(source: Bitmap): Bitmap = emptyBitmaps[source]!!
    fun clear(source: Bitmap) = emptyBitmaps.remove(source)


    val filters = ArrayList<FilterEntity>()

    init {

        filters.add(object : FilterEntity {
            override fun name(): String = "1"
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter1(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "2"
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter2(source, result)
                return result
            }
        })

    }


    private fun filter1(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                2.55f, 0f, 0f, 0f, -255f,
                0f, 2.55f, 0f, 0f, -255f,
                0f, 0f, 2.55f, 0f, -255f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply {
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                colorFilter = filter
                drawBitmap(source, 0f, 0f, this)
            }
        }
    }


    private fun filter2(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, -255f,
                0f, 3f, 0f, 0f, -255f,
                0f, 0f, 1f, 0f, -255f,
                0f, 0f, 0f, 3f, 0f
            )
        )
        Canvas(empty).apply {
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                colorFilter = filter
                drawBitmap(source, 0f, 0f, this)
            }
        }
    }


    private fun clearEmptyBitmap(empty: Bitmap) = Canvas(empty).drawColor(Color.TRANSPARENT)


}