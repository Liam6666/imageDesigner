package yzx.app.image.design.ui

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import com.blankj.utilcode.util.FileIOUtils
import yzx.app.image.design.utils.application
import yzx.app.image.design.utils.toast
import yzx.app.image.permissionutil.PermissionRequester
import java.io.ByteArrayOutputStream
import java.io.File


interface IImageDesignActivity


val cacheDir = File(application.filesDir, "BMP_CACHE")
val saveDir = File(Environment.getExternalStorageDirectory(), "ImageXX")


fun IImageDesignActivity.makeStatusBar() {
    (this as Activity).window.statusBarColor = Color.BLACK
}


fun IImageDesignActivity.startSaveBitmap(bitmap: Bitmap) {
    PermissionRequester.request(this as Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) { result ->
        if (result) {
            saveDir.mkdir()
            val targetFile = File(saveDir, "ImageXX_${System.currentTimeMillis()}.png")
            val out = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            FileIOUtils.writeFileFromBytesByStream(targetFile, out.toByteArray())
            toast("已保存到相册")
            finish()
        } else {
            toast("未授予存储权限")
        }
    }
}


fun IImageDesignActivity.cacheBitmap(bitmap: Bitmap) {

}