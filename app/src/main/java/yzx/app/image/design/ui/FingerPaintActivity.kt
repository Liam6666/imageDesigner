package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_finger_paint.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
import yzx.app.image.design.utils.dp2px
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
                makeUI()
                makeCanvas(originBitmap)
            }
        }
    }


    private fun makeUI() {
        rSeekBar.max = 255
        gSeekBar.max = 255
        bSeekBar.max = 255
        aSeekBar.max = 255
        lineSeekBar.max = 19
        val listener = object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = makeCircleViewAndLineUI()
        }
        rSeekBar.setOnSeekBarChangeListener(listener)
        gSeekBar.setOnSeekBarChangeListener(listener)
        bSeekBar.setOnSeekBarChangeListener(listener)
        aSeekBar.setOnSeekBarChangeListener(listener)
        lineSeekBar.setOnSeekBarChangeListener(listener)
        rSeekBar.progress = 255
        gSeekBar.progress = 255
        bSeekBar.progress = 255
        aSeekBar.progress = 255
        lineSeekBar.progress = 5
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
    }

    private fun getColor(): Int = Color.argb(255, rSeekBar.progress, gSeekBar.progress, bSeekBar.progress)
    private fun getLineDP(): Int = lineSeekBar.progress + 1
    private fun getAlpha(): Int = aSeekBar.progress

    private fun makeCircleViewAndLineUI() {
        paint.color = getColor()
        paint.alpha = getAlpha()
        paint.strokeWidth = dp2px(getLineDP()).toFloat()
        colorCircle.setColor(paint.color)
        colorCircle.alpha = paint.alpha / 255f
        line.setBackgroundColor(paint.color)
        line.alpha = colorCircle.alpha
        line.pivotX = 0f
        line.pivotY = 0f
        line.scaleY = paint.strokeWidth
    }


    private fun makeCanvas(originBitmap: Bitmap) {

    }


}