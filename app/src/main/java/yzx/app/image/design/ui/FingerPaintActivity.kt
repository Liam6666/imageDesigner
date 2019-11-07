package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_finger_paint.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.*


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
                onBitmapLoadedError()
            } else {
                image.setImageBitmap(originBitmap)
                makeUI(originBitmap)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        saveLastSetting()
    }


    private fun makeUI(originBmp: Bitmap) {
        lineSeekBar.max = 15
        lineSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                line.setBackgroundColor(colorPicker.getCurrentColor())
                line.pivotX = 0f; line.pivotY = 0f
                line.scaleY = getLineWidth()
            }
        })
        colorPicker.onColorChanged = { newColor -> line.setBackgroundColor(newColor) }
        restoreLastSetting()
        save.setOnClickListener { createBmp(originBmp) { newBmp -> newBmp?.run { startSaveBitmap(newBmp) } } }
        cache.setOnClickListener { createBmp(originBmp) { newBmp -> newBmp?.run { cacheBitmap(newBmp) } } }
        clear.setOnClickListener { paintView.clear() }
        back1.setOnClickListener { paintView.back1() }
        initPaintView(originBmp)
    }


    private fun getLineWidth(): Float = dp2px(lineSeekBar.progress + 1).toFloat()

    private fun initPaintView(bmp: Bitmap) {
        paintView.onNewPaintCreated = { p ->
            p.style = Paint.Style.STROKE
            p.strokeCap = Paint.Cap.ROUND
            p.strokeWidth = getLineWidth()
            p.color = colorPicker.getCurrentColor()
            p.pathEffect = CornerPathEffect(p.strokeWidth)
        }
        image.getRealImageWidthAndHeight { result ->
            paintView.layoutParams.width = result.x
            paintView.layoutParams.height = result.y
            paintView.requestLayout()
            runCacheOutOfMemory({ paintView.bitmap = Bitmap.createBitmap(result.x, result.y, Bitmap.Config.ARGB_8888) }) {
                toastMemoryError()
                finish()
            }
        }
    }


    private fun createBmp(originBmp: Bitmap, cb: (Bitmap?) -> Unit) {
        if (paintView.bitmap == null)
            return
        runCacheOutOfMemory({ cb.invoke(makeSameScaleAndOverlayBitmap(originBmp, paintView.bitmap!!)) }) {
            toastMemoryError()
            cb.invoke(null)
        }
    }


    private fun restoreLastSetting() {
        localStorage.get("finger_paint_setting_color")?.runCatching { colorPicker.setColor(toInt()) }
        localStorage.get("finger_paint_setting_width")?.runCatching { lineSeekBar.progress = toInt() }
    }


    private fun saveLastSetting() {
        val lineWidth = lineSeekBar.progress
        val color = colorPicker.getCurrentColor()
        localStorage.save("finger_paint_setting_color", color.toString())
        localStorage.save("finger_paint_setting_width", lineWidth.toString())
    }

}