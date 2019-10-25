package yzx.app.image.design.ui

import android.annotation.SuppressLint
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


    @SuppressLint("SetTextI18n")
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

                left1.setOnClickListener {
                    image.rotation -= 1
                    fixRotationDegree()
                    syncRotationDegreeToSeekBar()
                }
                left10.setOnClickListener {
                    image.rotation -= 10
                    fixRotationDegree()
                    syncRotationDegreeToSeekBar()
                }
                right1.setOnClickListener {
                    image.rotation += 1
                    fixRotationDegree()
                    syncRotationDegreeToSeekBar()
                }
                right10.setOnClickListener {
                    image.rotation += 10
                    fixRotationDegree()
                    syncRotationDegreeToSeekBar()
                }

                complete.setOnClickListener {
                    makeBitmap(originBitmap)?.run { startSaveBitmap(this) }
                }
                cache.setOnClickListener {
                    makeBitmap(originBitmap)?.run { cacheBitmap(this) }
                }
                returnOrigin.setOnClickListener {
                    reset()
                }

                rotationSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        image.rotation = progress.toFloat()
                        rotationText.text = "旋转角度: (${progress}°)"
                    }
                })
                whScaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        val scale = getScaleByProgress(progress)
                        val ws = scale[0]
                        val hs = scale[1]
                        if (ws == hs) {
                            image.scaleX = 1f
                            image.scaleY = 1f
                        } else if (ws > hs) {
                            image.scaleX = 1f
                            image.scaleY = hs / ws.toFloat()
                        } else {
                            image.scaleX = ws / hs.toFloat()
                            image.scaleY = 1f
                        }
                        whScaleText.text = "宽高比例: (${ws}:${hs})"
                    }
                })

                reset()
            }
        }
    }


    /* 同步旋转SeekBar的数值 */
    private fun syncRotationDegreeToSeekBar() {
        rotationSeekBar.progress = image.rotation.toInt()
    }

    /* 将旋转角度转化为0-359之间 */
    private fun fixRotationDegree() {
        if (image.rotation < 0) {
            image.rotation = 360 + image.rotation
        } else if (image.rotation > 359) {
            image.rotation = image.rotation - 360
        }
    }


    private val scaleRadius = 5
    private fun getScaleProgressMax(): Int = (scaleRadius - 1) * 2
    private fun getScaleByProgress(p: Int): Array<Int> {
        if (p == scaleRadius - 1)
            return arrayOf(scaleRadius, scaleRadius)
        else if (p < scaleRadius - 1)
            return arrayOf(p + 1, scaleRadius)
        else
            return arrayOf(scaleRadius, scaleRadius - (p - (scaleRadius - 1)))
    }


    /*
     * 重置
     */
    @SuppressLint("SetTextI18n")
    private fun reset() {
        image.scaleX = 1f
        image.scaleY = 1f
        image.rotation = 0f
        rotationSeekBar.max = 359
        rotationSeekBar.progress = 0
        whScaleSeekBar.max = getScaleProgressMax()
        whScaleSeekBar.progress = whScaleSeekBar.max / 2
        rotationText.text = "旋转角度: (0°)"
        whScaleText.text = "宽高比例: (${scaleRadius}:${scaleRadius})"
    }


    /*
     * 生成bitmap
     */
    private fun makeBitmap(source: Bitmap): Bitmap? {
        return try {
            val scaleBmp = makeScaleBitmap(source, image.scaleX, image.scaleY)!!
            makeRotatingBitmap(scaleBmp, image.rotation)
        } catch (e: OutOfMemoryError) {
            toastMemoryError()
            null
        }
    }


}