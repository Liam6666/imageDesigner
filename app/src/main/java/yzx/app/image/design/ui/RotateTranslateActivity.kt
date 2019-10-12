package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rotate_translate.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeBitmapWithMaxLength
import yzx.app.image.design.utils.launchActivity
import yzx.app.image.design.utils.makeRotatingBitmap
import yzx.app.image.design.utils.toast


class RotateTranslateActivity : AppCompatActivity(), IImageDesignActivity {


    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<RotateTranslateActivity>("f" to path)
    }


    private val filePath: String? by lazy { intent?.getStringExtra("f") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isNullOrEmpty()) {
            finish()
        } else {
            makeStatusBar()
            setContentView(R.layout.activity_rotate_translate)
            makeUI()
        }
    }


    private var originBitmap: Bitmap? = null

    private fun makeUI() {
        originBitmap = decodeBitmapWithMaxLength(filePath!!, 800)
        if (originBitmap == null) {
            toast("图片有误, 请重新选择")
            finish()
        } else {
            image.setImageBitmap(makeRotatingBitmap(originBitmap!!, 45f))

        }
    }

}