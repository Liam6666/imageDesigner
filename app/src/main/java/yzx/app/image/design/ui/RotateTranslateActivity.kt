package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rotate_translate.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.*


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

                xScaleSeekBar.progress = scaleArray.size / 2
                xScaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        image.scaleX = getScaleValueByProgress(progress)
                        xScaleText.text = "X轴缩放: (${image.scaleX})"
                    }
                })
                yScaleSeekBar.progress = scaleArray.size / 2
                yScaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        image.scaleY = getScaleValueByProgress(progress)
                        yScaleText.text = "Y轴缩放: (${image.scaleY})"
                    }
                })
            }
        }
    }


    private fun getScaleValueByProgress(p: Int): Float = scaleArray[p]
    private val scaleArray = arrayOf(
        0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f,
        1.1f, 1.2f, 1.3f, 1.4f, 1.5f, 1.6f, 1.7f, 1.8f, 1.9f, 2.0f
    )


    /*
     * 生成bitmap
     */
    private fun makeBitmap(source: Bitmap): Bitmap {
        val scaleBmp = makeScaleBitmap(source, image.scaleX, image.scaleY)!!
        return makeRotatingBitmap(scaleBmp, image.rotation)
    }


}