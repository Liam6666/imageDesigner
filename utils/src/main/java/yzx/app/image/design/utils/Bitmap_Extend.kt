package yzx.app.image.design.utils

import android.content.Context
import android.graphics.*
import com.blankj.utilcode.util.FileIOUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin


/**
 * 获取图片文件宽高
 */
fun getFileBitmapWH(path: String): Array<Int>? {
    val op = BitmapFactory.Options()
    BitmapFactory.decodeFile(path, op.apply { inJustDecodeBounds = true })
    val w = op.outWidth
    val h = op.outHeight
    if (w <= 0 || h <= 0)
        return null
    return arrayOf(w, h)
}


/**
 * 限制最大长度获取图片
 */
fun decodeFileBitmapWithMaxLength(context: Context = application, path: String, max: Int, cb: (Bitmap?) -> Unit) {
    val wh = getFileBitmapWH(path)
    if (wh == null) {
        cb.invoke(null)
    } else {
        var w = wh[0]
        var h = wh[1]
        if (w * h > max * max) {
            w = Math.min(max, w)
            h = Math.min(max, h)
        }
        GlobalScope.launch(Dispatchers.Default) {
            val bmp = Glide.with(context).asBitmap().load(File(path)).diskCacheStrategy(DiskCacheStrategy.NONE).submit(w, h).get()
            launch(Dispatchers.Main) { cb.invoke(bmp) }
        }
    }
}


// 内存中bitmap的最大像素面积
val max_bitmap_size = 4000 * 4000


/**
 * 获取原图bitmap
 */
fun decodeFileBitmap(path: String, cb: (bmp: Bitmap?, OutOfMemory: Boolean, tooLarge: Boolean) -> Unit) {
    try {
        val wh = getFileBitmapWH(path)
        if (wh == null) {
            cb.invoke(null, false, false)
        } else if (wh[0] * wh[1] > max_bitmap_size) {
            cb.invoke(null, false, true)
        } else {
            cb.invoke(BitmapFactory.decodeFile(path, BitmapFactory.Options().apply { inMutable = true }), false, false)
        }
    } catch (e: OutOfMemoryError) {
        cb.invoke(null, true, false)
    }
}


/**
 * 获取指定角度旋转的bitmap
 */
fun makeRotatingBitmap(source: Bitmap, degree: Float, useOriginIfNoChange: Boolean = true): Bitmap {
    if (degree < 0 || degree > 359)
        throw IllegalStateException("degree must in 0 - 359")
    if (degree == 0f)
        return if (useOriginIfNoChange) source else Bitmap.createBitmap(source)
    var resultW: Int
    var resultH: Int
    if (degree % 90f == 0f) {
        resultW = if (degree % 180f == 0f) source.width else source.height
        resultH = if (degree % 180f == 0f) source.height else source.width
    } else {
        val realDeg = Math.toRadians(
            if (degree > 180) {
                (degree - 180).toDouble() % 90
            } else {
                degree.toDouble() % 90
            }
        )
        val x1 = (source.width / 2) * cos(realDeg) - (-source.height / 2) * sin(realDeg)
        val y1 = (source.width / 2) * sin(realDeg) - (-source.height / 2) * cos(realDeg)
        val x2 = (source.width / 2) * cos(realDeg) - (-source.height / 2) * sin(realDeg)
        val y2 = (source.width / 2) * sin(realDeg) - (-source.height / 2) * cos(realDeg)
        resultW = abs(max(x1 * 2, x2 * 2)).toInt()
        resultH = abs(max(y1 * 2, y2 * 2)).toInt()
        if (degree > 90 && degree < 180 || degree > 270 && degree < 360) {
            val ow = resultW
            resultW = resultH
            resultH = ow
        }
    }
    val result = Bitmap.createBitmap(resultW, resultH, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(result)
    canvas.rotate(degree, result.width / 2f, result.height / 2f)
    canvas.drawBitmap(source, (result.width - source.width) / 2f, (result.height - source.height) / 2f, Paint(Paint.ANTI_ALIAS_FLAG))
    return result
}


/**
 * 获取指定scaleXY的bitmap
 */
fun makeScaleBitmap(source: Bitmap, x: Float, y: Float, useOriginIfNoChange: Boolean = true): Bitmap? {
    if (x == 1f && y == 1f)
        return if (useOriginIfNoChange) source else Bitmap.createBitmap(source)
    val newWidth = source.width * x
    val newHeight = source.height * y
    if (newWidth == 0f || newHeight == 0f)
        return null
    return Bitmap.createBitmap(newWidth.toInt(), newHeight.toInt(), Bitmap.Config.ARGB_8888).apply {
        val canvas = Canvas(this)
        canvas.drawBitmap(source, Matrix().apply { postScale(x, y) }, null)
    }
}


/**
 * 保存到文件
 */
fun Bitmap.saveToFile(file: File): Boolean {
    val out = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, out)
    return FileIOUtils.writeFileFromBytesByStream(file, out.toByteArray())
}


/**
 * 将上层图片缩放到底图同样大小并叠加图片, 形成新图
 */
fun makeSameScaleAndOverlayBitmap(source: Bitmap, overlay: Bitmap): Bitmap {
    val result = Bitmap.createBitmap(source)
    val canvas = Canvas(result)
    canvas.drawBitmap(overlay, Matrix().apply {
        postScale(source.width.toFloat() / overlay.width.toFloat(), source.height.toFloat() / overlay.height.toFloat())
    }, null)
    return result
}


/**
 * 截图, 参数为原图的比例截取值, 范围0f~1f
 */
@Deprecated(message = "效率低下", level = DeprecationLevel.WARNING)
fun makeClipBitmap(source: Bitmap, leftP: Float, topP: Float, widthP: Float, heightP: Float, useOriginIfNoChange: Boolean = true): Bitmap {
    check(leftP >= 0f && leftP + widthP <= 1f && widthP <= 1f && topP >= 0f && topP + heightP <= 1f && heightP <= 1f) { "params value error" }
    if (leftP == 0f && topP == 0f && heightP == 1f && widthP == 1f)
        return if (useOriginIfNoChange) source else Bitmap.createBitmap(source)
    val byteLength: Int
    val d = BitmapRegionDecoder.newInstance(ByteArrayOutputStream().run {
        source.compress(Bitmap.CompressFormat.PNG, 100, this)
        toByteArray().apply { byteLength = this.size }
    }, 0, byteLength, true)
    return d.decodeRegion(Rect().apply {
        left = (source.width * leftP).toInt()
        top = (source.height * topP).toInt()
        right = (left + source.width * widthP).toInt()
        bottom = (top + source.height * heightP).toInt()
    }, BitmapFactory.Options().apply { inPreferredConfig = Bitmap.Config.ARGB_8888 })
}


/**
 * 截图 (使用BitmapRegionDecoder有点太占用内存和cpu)
 */
fun makeClipBitmap2(source: Bitmap, leftP: Float, topP: Float, widthP: Float, heightP: Float, useOriginIfNoChange: Boolean = true): Bitmap {
    check(leftP >= 0f && leftP + widthP <= 1f && widthP <= 1f && topP >= 0f && topP + heightP <= 1f && heightP <= 1f) { "params value error" }
    if (leftP == 0f && topP == 0f && heightP == 1f && widthP == 1f)
        return if (useOriginIfNoChange) source else Bitmap.createBitmap(source)
    val resultBmp = Bitmap.createBitmap((source.width * widthP).toInt(), (source.height * heightP).toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(resultBmp)
    canvas.drawBitmap(source, -source.width * leftP, -source.height * topP, null)
    return resultBmp
}














