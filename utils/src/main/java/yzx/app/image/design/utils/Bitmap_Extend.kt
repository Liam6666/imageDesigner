package yzx.app.image.design.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory


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
fun makeRotatingBitmap(source: Bitmap, degree: Int): Bitmap {


    return source
}