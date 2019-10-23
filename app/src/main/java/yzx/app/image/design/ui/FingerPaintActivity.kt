package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
import yzx.app.image.design.utils.launchActivity
import yzx.app.image.design.utils.toast


class FingerPaintActivity : AppCompatActivity(), IImageDesignActivity {

    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<FingerPaintActivity>("f" to path)
    }


    private val filePath: String? by lazy { intent?.getStringExtra("f") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isNullOrEmpty()) {
            finish()
            return
        }
        makeStatusBar()
        setContentView(R.layout.activity_finger_paint)
        decodeFileBitmapWithMaxLength(this, filePath!!, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
            if (originBitmap == null) {
                toast("图片有误, 请重新选择")
                finish()
            } else {
                makeUI(originBitmap)
            }
        }
    }


    private fun makeUI(originBitmap: Bitmap) {


    }


}