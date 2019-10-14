package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rotate_translate.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
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


    private fun makeUI() {
        decodeFileBitmapWithMaxLength(this, filePath!!, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
            if (originBitmap == null) {
                toast("图片有误, 请重新选择")
                finish()
            } else {
                image.apply {
                    setImageBitmap(originBitmap)
                    post { pivotX = image.width / 2f; pivotY = image.height / 2f }
                }
                left1.setOnClickListener { image.rotation -= 1 }
                left10.setOnClickListener { image.rotation -= 10 }
                right1.setOnClickListener { image.rotation += 1 }
                right10.setOnClickListener { image.rotation += 10 }

                returnOrigin.setOnClickListener { image.rotation = 0f }
                cache.setOnClickListener { cacheBitmap(makeBitmap(originBitmap)) }
                complete.setOnClickListener { startSaveBitmap(makeBitmap(originBitmap)) }
            }
        }
    }


    /*
     * 生成bitmap
     */
    private fun makeBitmap(source: Bitmap): Bitmap {
        return makeRotatingBitmap(source, image.rotation)
    }


}