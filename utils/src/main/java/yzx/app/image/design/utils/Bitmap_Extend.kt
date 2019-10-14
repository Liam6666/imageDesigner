package yzx.app.image.design.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


fun getFileBitmapWH(path: String): Array<Int>? {
    val op = BitmapFactory.Options()
    BitmapFactory.decodeFile(path, op.apply { inJustDecodeBounds = true })
    val w = op.outWidth
    val h = op.outHeight
    if (w <= 0 || h <= 0)
        return null
    return arrayOf(w, h)
}


fun decodeFileBitmapWithMaxLength(context: Context = application, path: String, max: Int = 1920, cb: (Bitmap?) -> Unit) {
    val wh = getFileBitmapWH(path)
    if (wh == null) {
        cb.invoke(null)
    } else {
        val w = Math.min(wh[0], max)
        val h = Math.min(wh[1], max)
        GlobalScope.launch(Dispatchers.Default) {
            val bmp = Glide.with(context).asBitmap().load(File(path)).submit(w, h).get()
            launch(Dispatchers.Main) { cb.invoke(bmp) }
        }
    }
}


/**
 * 获取指定角度旋转的bitmap
 */
fun makeRotatingBitmap(source: Bitmap, degree: Float): Bitmap {


    return source
}