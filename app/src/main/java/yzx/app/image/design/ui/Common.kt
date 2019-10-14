package yzx.app.image.design.ui

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import yzx.app.image.design.logicViews.dismissWaitingDialog
import yzx.app.image.design.logicViews.showWaitingDialog
import yzx.app.image.design.utils.application
import yzx.app.image.design.utils.longToast
import yzx.app.image.design.utils.toast
import yzx.app.image.permissionutil.PermissionRequester
import java.io.ByteArrayOutputStream
import java.io.File


object BitmapDecodeOptions {
    var decodeBitmapMaxLength = 1080
}


interface IImageDesignActivity


val cacheDir = File(application.filesDir, "BMP_CACHE")
val saveDir = File(PathUtils.getExternalPicturesPath(), "ImageXO")


fun IImageDesignActivity.makeStatusBar() {
    (this as Activity).window.statusBarColor = Color.BLACK
}


fun IImageDesignActivity.startSaveBitmap(bitmap: Bitmap) {
    PermissionRequester.request(this as Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) { result ->
        val act = this as Activity
        if (result) {
            act.showWaitingDialog()
            GlobalScope.launch {
                saveDir.mkdir()
                val targetFile = File(saveDir, "ImageXO_${System.currentTimeMillis()}.png")
                val out = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                FileIOUtils.writeFileFromBytesByStream(targetFile, out.toByteArray())
                FileUtils.notifySystemToScan(targetFile)
                launch(Dispatchers.Main) {
                    act.dismissWaitingDialog()
                    longToast("已保存到系统相册ImageXO文件夹中")
                    finish()
                }
            }
        } else {
            toast("未授予存储权限")
        }
    }
}


fun IImageDesignActivity.cacheBitmap(bitmap: Bitmap) {

}