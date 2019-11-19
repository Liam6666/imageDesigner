package yzx.app.image.design.util

import android.graphics.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.application
import yzx.app.image.design.utils.runCacheOutOfMemory
import java.util.*
import kotlin.collections.ArrayList


interface FilterEntity {
    fun todo(source: Bitmap): Bitmap
    fun name(): String
    fun getThumbnail(): Bitmap
}


object ImageFilters {

    private val emptyBitmaps = WeakHashMap<Bitmap, Bitmap>()
    private fun clearEmptyBitmap(empty: Bitmap) = Canvas(empty).drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
    fun makeEmptyBitmap(source: Bitmap, onMemoryError: () -> Unit) {
        runCacheOutOfMemory({
            val empty = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            emptyBitmaps[source] = empty
        }) { onMemoryError.invoke() }
    }


    fun getResult(source: Bitmap): Bitmap = emptyBitmaps[source]!!
    fun clear(source: Bitmap) = emptyBitmaps.remove(source)
    val filters = ArrayList<FilterEntity>()


    private val thumbnailBitmapSource = BitmapFactory.decodeResource(application.resources, R.drawable.filter_thumbnail, BitmapFactory.Options().apply {
        inSampleSize = 2
    })


    init {

        filters.add(object : FilterEntity {
            override fun name(): String = "1"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter1(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "2"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter2(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "3"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter3(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "4"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter4(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "5"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter5(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "6"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter6(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "7"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter7(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "8"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter8(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "9"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter9(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun name(): String = "10"
            override fun getThumbnail(): Bitmap = thumbnailBitmapSource
            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter10(source, result)
                return result
            }
        })
    }


    private fun filter1(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                2.3f, 0f, 0f, 0f, -255f,
                0f, 2.3f, 0f, 0f, -255f,
                0f, 0f, 2.3f, 0f, -255f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter2(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 2.3f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter3(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                2.3f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter4(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 2.3f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter5(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                2.3f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 2.3f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }


    private fun filter6(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 2.3f, 0f, 0f, 0f,
                0f, 0f, 2.3f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter7(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                2.3f, 0f, 0f, 0f, 0f,
                0f, 2.3f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter8(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                0.5f, 0f, 0f, 0f, 0f,
                0f, 0.5f, 0f, 0f, 0f,
                0f, 0f, 0.5f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter9(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                2f, 0f, 0f, 0f, 0f,
                0f, 2f, 0f, 0f, 0f,
                0f, 0f, 2f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    private fun filter10(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(ColorMatrix().apply {
            setSaturation(0f)
        })
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

}