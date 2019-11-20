package yzx.app.image.design.util

import android.graphics.*
import androidx.renderscript.Element
import yzx.app.image.design.utils.application
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.runCacheOutOfMemory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min


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

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter11(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter11(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter12(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter12(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.RGB_565)
                    .apply { filter13(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter13(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.ARGB_4444)
                    .apply { filter14(getSourceThumbnail(source), this, 5f) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter14(source, result, 18f)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.ARGB_4444)
                    .apply { filter15(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter15(source, result)
                return result
            }
        })

        filters.add(object : FilterEntity {
            override fun getThumbnail(source: Bitmap): Bitmap =
                Bitmap.createBitmap(getSourceThumbnail(source).width, getSourceThumbnail(source).height, Bitmap.Config.ARGB_4444)
                    .apply { filter16(getSourceThumbnail(source), this) }

            override fun todo(source: Bitmap): Bitmap {
                val result = emptyBitmaps[source]!!
                clearEmptyBitmap(result)
                filter16(source, result)
                return result
            }
        })

    }


    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //


    // 锐化
    private fun filter1(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1.9f, 0f, 0f, 0f, -130f,
                0f, 1.9f, 0f, 0f, -130f,
                0f, 0f, 1.9f, 0f, -130f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 趋向绿色
    private fun filter2(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 1.44f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 趋向红色
    private fun filter3(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1.44f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 趋向蓝色
    private fun filter4(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1.44f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 趋向f0f颜色
    private fun filter5(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1.44f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1.44f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 趋向0ff颜色
    private fun filter6(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 1.44f, 0f, 0f, 0f,
                0f, 0f, 1.44f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 趋向ff0颜色
    private fun filter7(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1.44f, 0f, 0f, 0f, 0f,
                0f, 1.44f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 变暗
    private fun filter8(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                0.66f, 0f, 0f, 0f, 0f,
                0f, 0.66f, 0f, 0f, 0f,
                0f, 0f, 0.66f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 变亮
    private fun filter9(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1.24f, 0f, 0f, 0f, 0f,
                0f, 1.24f, 0f, 0f, 0f,
                0f, 0f, 1.24f, 0f, 0f,
                0f, 0f, 0f, 1.24f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 黑白
    private fun filter10(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(ColorMatrix().apply {
            setSaturation(0f)
        })
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 内阴影
    private fun filter11(source: Bitmap, empty: Bitmap) {
        Canvas(empty).apply {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { maskFilter = BlurMaskFilter(min(source.width / 2f, source.height / 2f), BlurMaskFilter.Blur.INNER) }
            paint.color = Color.BLACK
            drawBitmap(source, 0f, 0f, paint)
        }
    }

    // 反向
    private fun filter12(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 复古
    private fun filter13(source: Bitmap, empty: Bitmap) {
        val filter = ColorMatrixColorFilter(
            floatArrayOf(
                1 / 2f, 1 / 2f, 1 / 2f, 0f, 0f,
                1 / 3f, 1 / 3f, 1 / 3f, 0f, 0f,
                1 / 4f, 1 / 4f, 1 / 4f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        Canvas(empty).apply { Paint(Paint.ANTI_ALIAS_FLAG).apply { colorFilter = filter; drawBitmap(source, 0f, 0f, this) } }
    }

    // 高斯模糊
    private fun filter14(source: Bitmap, empty: Bitmap, radius: Float) {
        val rs = androidx.renderscript.RenderScript.create(application)
        val oa = androidx.renderscript.Allocation.createFromBitmap(rs, empty)
        val sc = androidx.renderscript.ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        sc.setRadius(radius)
        sc.setInput(androidx.renderscript.Allocation.createFromBitmap(rs, source))
        sc.forEach(oa)
        oa.copyTo(empty)
    }

    // 素描
    private fun filter15(source: Bitmap, empty: Bitmap) {

    }

    private fun filter16(source: Bitmap, empty: Bitmap) {

    }

}