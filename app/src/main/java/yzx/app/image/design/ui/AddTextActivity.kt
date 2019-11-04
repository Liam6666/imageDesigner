package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_text.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
import yzx.app.image.design.utils.getRealImageWidthAndHeight
import yzx.app.image.design.utils.launchActivity


class AddTextActivity : AppCompatActivity(), IImageDesignActivity {

    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<AddTextActivity>("f" to path)
    }


    private val filePath by lazy { intent?.getStringExtra("f") ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isEmpty()) {
            finish()
            return
        }
        makeStatusBar()
        setContentView(R.layout.activity_add_text)
        decodeFileBitmapWithMaxLength(this, filePath, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
            if (originBitmap == null) {
                onBitmapLoadedError()
            } else {
                setUI(originBitmap)
            }
        }
    }


    private fun setUI(originBitmap: Bitmap) {
        image.setImageBitmap(originBitmap)
        image.getRealImageWidthAndHeight { result ->
            textBox.layoutParams.width = result.x
            textBox.layoutParams.height = result.y
            textBox.requestLayout()
        }


    }


}