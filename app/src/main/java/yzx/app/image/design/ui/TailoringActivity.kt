package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_image_filter.*
import kotlinx.android.synthetic.main.activity_image_filter.image
import kotlinx.android.synthetic.main.fragment_rect_clip.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
import yzx.app.image.design.utils.launchActivity


class TailoringActivity : AppCompatActivity(), IImageDesignActivity {


    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<TailoringActivity>("f" to path)
    }


    private val filePath: String? by lazy { intent?.getStringExtra("f") }
    private var sourceBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isNullOrEmpty()) {
            finish()
        } else {
            makeStatusBar()
            setContentView(R.layout.activity_tailoring)
            decodeFileBitmapWithMaxLength(this, filePath!!, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
                sourceBitmap = originBitmap
                if (originBitmap == null) onBitmapLoadedError() else makeUI(originBitmap)
            }
        }
    }

    private fun makeUI(originBitmap: Bitmap) {
        RectClipFragment().apply {
            bmp = originBitmap
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, this).commitNowAllowingStateLoss()
            this@TailoringActivity.cache.setOnClickListener { getResult()?.run { cacheBitmap(this) } }
            this@TailoringActivity.save.setOnClickListener { getResult()?.run { startSaveBitmap(this) } }
        }

    }


    class RectClipFragment : Fragment() {
        lateinit var bmp: Bitmap

        /** 获取结果 */
        fun getResult(): Bitmap? {
            return bmp
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_rect_clip, container, false)
        }


        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            image.setImageBitmap(bmp)
            clipRect.bindImageView(image)
        }

    }


}