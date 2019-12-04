package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_area_larger.*
import kotlinx.coroutines.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.*


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


    private val repeatScope = MainScope()

    private fun makeUI(originBitmap: Bitmap) {
        window.decorView.post {

            var maxPaddingWH = 0
            var maxPaddingTB = 0

            image.setImageBitmap(originBitmap)
            image.getRealImageWidthAndHeight {
                image.layoutParams.width = it.x
                image.layoutParams.height = it.y
                image.requestLayout()
                image.scaleType = ImageView.ScaleType.FIT_XY
                image.setBackgroundColor(colorPicker.getCurrentColor())
                maxPaddingWH = it.x / 4
                maxPaddingTB = it.y / 4
            }

            colorPicker.onColorChanged = { image.setBackgroundColor(colorPicker.getCurrentColor()) }
            val gap = dp2px(2)

            l_add.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                val targetPaddingLeft = image.paddingLeft + gap
                if (targetPaddingLeft > maxPaddingWH) toast("已达最大")
                else image.setPadding(targetPaddingLeft, image.paddingTop, image.paddingRight, image.paddingBottom)
            }
            l_reduce.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                if (image.paddingLeft == 0) return@setOnClickListener
                var targetPaddingLeft = image.paddingLeft - gap
                targetPaddingLeft = if (targetPaddingLeft < 0) 0 else targetPaddingLeft
                image.setPadding(targetPaddingLeft, image.paddingTop, image.paddingRight, image.paddingBottom)
            }
            t_add.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                val targetPaddingTop = image.paddingTop + gap
                if (targetPaddingTop > maxPaddingTB) toast("已达最大")
                else image.setPadding(image.paddingLeft, targetPaddingTop, image.paddingRight, image.paddingBottom)
            }
            t_reduce.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                if (image.paddingTop == 0) return@setOnClickListener
                var targetPaddingTop = image.paddingTop - gap
                targetPaddingTop = if (targetPaddingTop < 0) 0 else targetPaddingTop
                image.setPadding(image.paddingLeft, targetPaddingTop, image.paddingRight, image.paddingBottom)
            }
            r_add.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                val targetPaddingRight = image.paddingRight + gap
                if (targetPaddingRight > maxPaddingWH) toast("已达最大")
                else image.setPadding(image.paddingLeft, image.paddingTop, targetPaddingRight, image.paddingBottom)
            }
            r_reduce.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                if (image.paddingRight == 0) return@setOnClickListener
                var targetPaddingRight = image.paddingRight - gap
                targetPaddingRight = if (targetPaddingRight < 0) 0 else targetPaddingRight
                image.setPadding(image.paddingLeft, image.paddingTop, targetPaddingRight, image.paddingBottom)
            }
            b_add.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                val targetPaddingBottom = image.paddingBottom + gap
                if (targetPaddingBottom > maxPaddingTB) toast("已达最大")
                else image.setPadding(image.paddingLeft, image.paddingTop, image.paddingRight, targetPaddingBottom)
            }
            b_reduce.setOnClickListener {
                if (maxPaddingWH == 0 || maxPaddingTB == 0) return@setOnClickListener
                if (image.paddingBottom == 0) return@setOnClickListener
                var targetPaddingBottom = image.paddingBottom - gap
                targetPaddingBottom = if (targetPaddingBottom < 0) 0 else targetPaddingBottom
                image.setPadding(image.paddingLeft, image.paddingTop, image.paddingRight, targetPaddingBottom)
            }

            cache.setOnClickListener { getResultBitmap(originBitmap)?.run { cacheBitmap(this) } }
            save.setOnClickListener { getResultBitmap(originBitmap)?.run { startSaveBitmap(this) } }

            var currentRepeatJob: Job? = null
            fun performRepeat(view: View) {
                view.setOnLongClickListener {
                    currentRepeatJob?.cancel()
                    currentRepeatJob = repeatScope.launch { repeat(200) { view.performClick(); delay(100) } }
                    true
                }
            }
            performRepeat(l_add)
            performRepeat(l_reduce)
            performRepeat(t_add)
            performRepeat(t_reduce)
            performRepeat(r_add)
            performRepeat(r_reduce)
            performRepeat(b_add)
            performRepeat(b_reduce)
            val touchListener = View.OnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> currentRepeatJob?.cancel()
                }; false
            }
            l_add.setOnTouchListener(touchListener)
            l_reduce.setOnTouchListener(touchListener)
            t_add.setOnTouchListener(touchListener)
            t_reduce.setOnTouchListener(touchListener)
            r_add.setOnTouchListener(touchListener)
            r_reduce.setOnTouchListener(touchListener)
            b_add.setOnTouchListener(touchListener)
            b_reduce.setOnTouchListener(touchListener)
        }
    }


    /**
     * 获取结果bitmap
     */
    private fun getResultBitmap(originBitmap: Bitmap): Bitmap? {
        if (image.paddingLeft + image.paddingRight + image.paddingTop + image.paddingBottom < 1) {
            toast("图片无改动")
            return null
        }
        runCacheOutOfMemory({
            return makeExpendBitmap(
                originBitmap, colorPicker.getCurrentColor(),
                image.paddingTop.toFloat() / image.height, image.paddingRight.toFloat() / image.width,
                image.paddingBottom.toFloat() / image.height, image.paddingLeft.toFloat() / image.width
            )
        }) { toastMemoryError() }
        return null
    }


    override fun onDestroy() {
        super.onDestroy()
        repeatScope.cancel()
    }

}