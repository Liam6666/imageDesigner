package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
import yzx.app.image.design.utils.launchActivity


class PureColorMakerActivity : AppCompatActivity(), IImageDesignActivity {


    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<PureColorMakerActivity>("f" to path)
    }


    private val filePath: String? by lazy { intent?.getStringExtra("f") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isNullOrEmpty()) {
            finish()
        } else {
            makeStatusBar()
            setContentView(R.layout.activity_pure_color_maker)
            decodeFileBitmapWithMaxLength(this, filePath!!, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
                if (originBitmap == null) onBitmapLoadedError() else makeUI(originBitmap)
            }
        }
    }


    private fun makeUI(originBitmap: Bitmap) {

    }


}