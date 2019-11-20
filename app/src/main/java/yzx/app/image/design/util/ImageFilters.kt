package yzx.app.image.design.util

import android.graphics.*
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.runCacheOutOfMemory
import java.util.*
import kotlin.collections.ArrayList


interface FilterEntity {
    fun todo(source: Bitmap): Bitmap
    fun getThumbnail(source: Bitmap): Bitmap
}


object ImageFilters {

    // 原图缩略图
    private val thumbnails = WeakHashMap<Bitmap, Bitmap>()
    // 原图同等大小的空图
    private val emptyBitmaps = WeakHashMap<Bitmap, Bitmap>()

    /* 清洗画板空图  */
    private fun clearEmptyBitmap(empty: Bitmap) = Canvas(empty).drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

    /* 获取原图缩略图 */
    private fun getSourceThumbnail(source: Bitmap) = thumbnails[source]!!

    /** 创建空画板图 */
    fun makeEmptyBitmap(source: Bitmap, onMemoryError: () -> Unit) {
        runCacheOutOfMemory({
            val empty = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
            emptyBitmaps[source] = empty
        }) { onMemoryError.invoke() }
    }

    /** 创建缩略图 */
    fun makeThumbnail(source: Bitmap) = thumbnails.put(source, Bitmap.createScaledBitmap(source, dp2px(40), dp2px(40), false))

    /** 获取结果 */
    fun getResult(source: Bitmap): Bitmap = emptyBitmaps[source]!!

    /** 清除画板图和缩略图 */
    fun clear(source: Bitmap) = { emptyBitmaps.remove(source); thumbnails.remove(source) }.invoke()

    // 全部过滤器
    val filters = ArrayList<FilterEntity>()


    init {

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter1(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter1(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter2(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter2(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter3(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter3(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter4(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter4(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter5(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter5(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter6(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter6(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter7(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter7(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter8(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter8(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter9(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter9(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter10(getSourceThumbnail(source), this) }

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