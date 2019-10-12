package yzx.app.image.design.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix


fun getFileBitmapWH(path: String): Array<Int>? {
    val op = BitmapFactory.Options()
    BitmapFactory.decodeFile(path, op.apply { inJustDecodeBounds = true })
    val w = op.outWidth
    val h = op.outHeight
    if (w <= 0 || h <= 0)
        return null
    return arrayOf(w, h)
}


fun decodeBitmapWithMaxLength(path: String, max: Int): Bitmap? {
    val wh = getFileBitmapWH(path)
    if (wh == null) {
        return null
    } else {
        val w = wh[0]
        val h = wh[1]
        if (w <= max && h < max) {
            return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply { inMutable = true })
        } else {
            val maxLen = Math.max(w, h)
            val scale = maxLen.div(max) + if (maxLen % max == 0) 0 else 1
            return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply {
                inSampleSize = scale
                inMutable = true
            })
        }
    }
}


/**
 * 获取指定角度旋转的bitmap
 */
fun makeRotatingBitmap(source: Bitmap, degree: Float): Bitmap {
    if (degree < 0f)
        throw IllegalStateException("fuck your blood mother pussy")
    if (degree == 0f || degree % 360f == 0f)
        return Bitmap.createBitmap(source)
    val computeWHDegree =
        if (degree % 90f == 0f) {
            if ((degree / 90f) % 2 == 0f) {
                0f
            } else {
                90f
            }
        } else {
            90 - (degree % 90f)
        }
    var w: Int
    var h: Int
    if (computeWHDegree == 0f) {
        w = source.height
        h = source.width
    } else if (computeWHDegree == 90f) {
        w = source.width
        h = source.height
    } else {
        val hypotenuse = Math.sqrt((source.width * source.width + source.height * source.height).toDouble()) / 2f
        "".run {
            val staticDeg_H = Math.atan(source.height.toDouble() / source.width)
            val deg = 90f - staticDeg_H - computeWHDegree
            h = (Math.cos(deg) * hypotenuse).toInt()
        }
        "".run {
            val staticDeg_W = Math.atan(source.width.toDouble() / source.height)
            val deg = 90f - staticDeg_W - computeWHDegree
            w = (Math.cos(deg) * hypotenuse).toInt()
        }
    }
    return Bitmap.createBitmap(source, 0, 0, w, h, Matrix().apply { postRotate(degree) }, false)
}