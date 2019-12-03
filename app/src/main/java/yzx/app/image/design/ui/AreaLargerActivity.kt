package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_area_larger.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
import yzx.app.image.design.utils.launchActivity


class AreaLargerActivity : AppCompatActivity(), IImageDesignActivity {

    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<AreaLargerActivity>("f" to path)
    }


    private val filePath: String? by lazy { intent?.getStringExtra("f") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isNullOrEmpty()) {
            finish()
        } else {
            makeStatusBar()
            setContentView(R.layout.activity_area_larger)
            decodeFileBitmapWithMaxLength(this, filePath!!, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
                if (originBitmap == null) onBitmapLoadedError() else makeUI(originBitmap)
            }
        }
    }


    private fun makeUI(originBitmap: Bitmap) {
        image.setImageBitmap(originBitmap)
        l_add.setOnClickListener { }
        l_reduce.setOnClickListener { }
        t_add.setOnClickListener { }
        t_reduce.setOnClickListener { }
        r_add.setOnClickListener { }
        r_reduce.setOnClickListener { }
        b_add.setOnClickListener { }
        b_reduce.setOnClickListener { }

        cache.setOnClickListener { }
        save.setOnClickListener { }
    }

}