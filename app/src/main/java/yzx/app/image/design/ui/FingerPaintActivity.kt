package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
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


    private fun makeUI(originBmp: Bitmap) {
        rSeekBar.max = 255
        gSeekBar.max = 255
        bSeekBar.max = 255
        lineSeekBar.max = 15

        val listener = object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = saveLastSetting()
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = makeCircleViewAndLineUI()
        }
        rSeekBar.setOnSeekBarChangeListener(listener)
        gSeekBar.setOnSeekBarChangeListener(listener)
        bSeekBar.setOnSeekBarChangeListener(listener)
        lineSeekBar.setOnSeekBarChangeListener(listener)

        restoreLastSetting()

        save.setOnClickListener { createBmp(originBmp) { newBmp -> newBmp?.run { startSaveBitmap(newBmp) } } }
        cache.setOnClickListener { createBmp(originBmp) { newBmp -> newBmp?.run { cacheBitmap(newBmp) } } }
        clear.setOnClickListener { paintView.clear() }
        back1.setOnClickListener { paintView.back1() }

        initPaintView(originBmp)
    }


    private fun getColor(): Int = Color.argb(255, rSeekBar.progress, gSeekBar.progress, bSeekBar.progress)
    private fun getLineWidth(): Float = dp2px(lineSeekBar.progress + 1).toFloat()

    private fun makeCircleViewAndLineUI() {
        colorCircle.setColor(getColor())
        line.setBackgroundColor(getColor())
        line.alpha = colorCircle.alpha
        line.pivotX = 0f
        line.pivotY = 0f
        line.scaleY = getLineWidth()
    }


    private fun initPaintView(bmp: Bitmap) {
        paintView.onNewPaintCreated = { p ->
            p.style = Paint.Style.STROKE
            p.strokeCap = Paint.Cap.ROUND
            p.strokeWidth = getLineWidth()
            p.color = getColor()
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
        var r = 255
        var g = 255
        var b = 255
        var l = 5
        localStorage.get("finger_paint_setting")?.runCatching {
            val arr = split("_")
            r = arr[0].toInt()
            g = arr[1].toInt()
            b = arr[2].toInt()
            l = arr[3].toInt()
        }
        rSeekBar.progress = r
        gSeekBar.progress = g
        bSeekBar.progress = b
        lineSeekBar.progress = l
    }

    private fun saveLastSetting() {
        val r = rSeekBar.progress
        val g = gSeekBar.progress
        val b = bSeekBar.progress
        val l = lineSeekBar.progress
        val value = "${r}_${g}_${b}_$l"
        localStorage.save("finger_paint_setting", value)
    }

}