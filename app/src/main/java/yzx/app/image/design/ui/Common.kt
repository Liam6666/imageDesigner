package yzx.app.image.design.ui

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yzx.app.image.design.logicViews.ImageCacheMgr
import yzx.app.image.design.logicViews.dismissWaitingDialog
import yzx.app.image.design.logicViews.showWaitingDialog
import yzx.app.image.design.utils.application
import yzx.app.image.design.utils.longToast
import yzx.app.image.design.utils.saveToFile
import yzx.app.image.design.utils.toast
import yzx.app.image.permissionutil.PermissionRequester
import java.io.File


object BitmapDecodeOptions {
    val decodeMaxLength_LOW = 760
    val decodeMaxLength_MIDDLE = 1280
    val decodeMaxLength_HIGH = 1920
    val decodeMaxLength_ORIGIN = Target.SIZE_ORIGINAL

    // 从本地加载到内存bitmap的最大像素值
    var decodeBitmapMaxLength = decodeMaxLength_HIGH
}


interface IImageDesignActivity


// 临时存储目录文件夹
val xo_ImageCacheDir = File(application.filesDir, "BMP_CACHES")
// 系统相册文件夹
val xo_ImageSaveDir = File(PathUtils.getExternalPicturesPath(), "ImageXO")


/**
 * 根据path加载图片有误
 */
fun IImageDesignActivity.onBitmapLoadedError() {
    toast("图片有误, 请重新选择")
    (this as Activity).finish()
}


/**
 * 让通知栏变黑色
 * */
fun IImageDesignActivity.makeStatusBar() {
    (this as Activity).window.statusBarColor = Color.BLACK
}


/**
 * 内存不够toast
 */
fun IImageDesignActivity.toastMemoryError() {
    toast("运行内存不足, 请重试")
}


private val backgroundScope = CoroutineScope(Dispatchers.Default)

/**
 * 开始存储图片到本地相册
 */
fun IImageDesignActivity.startSaveBitmap(bitmap: Bitmap) {
    PermissionRequester.request(this as Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) { result ->
        val act = this as Activity
        if (result) {
            act.showWaitingDialog()
            backgroundScope.launch {
                xo_ImageSaveDir.mkdir()
                val targetFile = File(xo_ImageSaveDir, "ImageXO_${System.currentTimeMillis()}.png")
                val saveResult = bitmap.saveToFile(targetFile)
                delay(getFakeDelayDuration(bitmap))
                launch(Dispatchers.Main) {
                    act.dismissWaitingDialog()
                    if (saveResult) {
                        FileUtils.notifySystemToScan(targetFile)
                        longToast("已保存到系统相册ImageXO文件夹中")
                        finish()
                    } else {
                        toast("保存失败, 存储空间不足")
                    }
                }
            }
        } else {
            toast("未授予存储权限")
        }
    }
}


/* 获取保存图片的延迟时间, 为了能完美暂时WaitingDialog的动画 */
private fun getFakeDelayDuration(bitmap: Bitmap): Long {
    val long = 1000 * 1000
    val middle = 2000 * 2000
    return when (bitmap.width * bitmap.height) {
        in 0 until long -> {
            900L
        }
        in 0 until middle -> {
            500L
        }
        else -> {
            200L
        }
    }
}


/**
 * 存储图片到临时缓存区
 */
fun IImageDesignActivity.cacheBitmap(bitmap: Bitmap) {
    val act = this as Activity
    act.showWaitingDialog()
    backgroundScope.launch {
        xo_ImageCacheDir.mkdir()
        val saveResult = bitmap.saveToFile(File(xo_ImageCacheDir, ImageCacheMgr.getCacheFileName()))
        delay(getFakeDelayDuration(bitmap))
        launch(Dispatchers.Main) {
            act.dismissWaitingDialog()
            if (saveResult) {
                longToast("图片已经保存到临时存储区")
                finish()
            } else {
                toast("保存失败, 存储空间不足")
            }
        }
    }
}