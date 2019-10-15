package yzx.app.image.design.utils

import android.content.Context
import android.graphics.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


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
            val bmp = Glide.with(context).asBitmap().load(File(path)).submit(w, h).get()
            launch(Dispatchers.Main) { cb.invoke(bmp) }
        }
    }
}


/**
 * 获取原图bitmap
 */
fun decodeFileBitmap(path: String, cb: (bmp: Bitmap?, OutOfMemory: Boolean) -> Unit) {
    try {
        val bmp = BitmapFactory.decodeFile(path, BitmapFactory.Options().apply { inMutable = true })
        cb.invoke(bmp, false)
    } catch (e: OutOfMemoryError) {
        cb.invoke(null, true)
    }
}


/**
 * 获取指定角度旋转的bitmap
 */
fun makeRotatingBitmap(source: Bitmap, degree: Float, useOriginIfNoChange: Boolean = true): Bitmap {
    if (degree % 360f == 0f)
        return if (useOriginIfNoChange) source else Bitmap.createBitmap(source)
    val resultW: Int
    val resultH: Int
    if (degree % 90f == 0f) {
        resultW = if (degree % 180f == 0f) source.width else source.height
        resultH = if (degree % 180f == 0f) source.height else source.width
    } else {
        resultW = Math.sqrt((source.width * source.width + source.height * source.height).toDouble()).toInt()
        resultH = resultW
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