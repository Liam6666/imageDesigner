package yzx.app.image.design.logicViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_add_text_panel.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.inflateView


class AddTextPanel : FrameLayout {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        inflateView(context, R.layout.layout_add_text_panel, this, true)
    }


    /** 修改文案按钮点击 */
    var onChangeTextButtonClick: (() -> Unit)? = null

    /** 获取关闭按钮 */
    var onCloseButtonClick: (() -> Unit)? = null

    /** 设置对应的TextView */
    fun setTextView(tv: TextView) {
        restoreTextViewUIInfo(tv)
        setUIEvent(tv)
    }

    /** 根据id获取内部任意View */
    fun getView(id: Int) = findViewById<View>(id)


    /* 设置UI事件 */
    @SuppressLint("SetTextI18n")
    private fun setUIEvent(tv: TextView) {
        repeat(container.childCount) { index -> container.getChildAt(index).apply { tag = alpha } }

        changeText.setOnClickListener { onChangeTextButtonClick?.invoke() }
        close.setOnClickListener { onCloseButtonClick?.invoke() }

        val rgbSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) = transparentAllExcept(rSeekBar, gSeekBar, bSeekBar, r, g, b, tv_color)
            override fun onStopTrackingTouch(seekBar: SeekBar) = transparentBack()
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val color = Color.rgb(rSeekBar.progress, gSeekBar.progress, bSeekBar.progress)
                tv_color.setTextColor(color)
                tv.setTextColor(color)
            }
        }
        rSeekBar.setOnSeekBarChangeListener(rgbSeekBarListener)
        gSeekBar.setOnSeekBarChangeListener(rgbSeekBarListener)
        bSeekBar.setOnSeekBarChangeListener(rgbSeekBarListener)

        val sizeSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) = transparentAllExcept(tv_size, sizeSeekBar)
            override fun onStopTrackingTouch(seekBar: SeekBar) = transparentBack()
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tv_size.text = "文字大小: (${progress})"
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, progress.toFloat())
            }
        }
        sizeSeekBar.setOnSeekBarChangeListener(sizeSeekBarListener)

    }


    /* 部分View透明 */
    private fun transparentAllExcept(vararg views: View) {
        repeat(container.childCount) { index ->
            val child = container.getChildAt(index)
            if (!views.contains(child)) {
                child.animate().cancel()
                child.animate().alpha(0f).setDuration(100).start()
            } else {
                child.alpha = child.alpha / 2f
            }
        }
    }

    /* 还原透明状态 */
    private fun transparentBack() {
        repeat(container.childCount) { index ->
            val child = container.getChildAt(index)
            val originAlpha = child.tag as Float
            child.animate().cancel()
            child.animate().alpha(originAlpha).setDuration(100).start()
        }
    }


    /* 根据TextView, 恢复UI显示的数据 */
    @SuppressLint("SetTextI18n")
    private fun restoreTextViewUIInfo(tv: TextView) {
        tv_text_content.text = tv.text

        rSeekBar.max = 255
        gSeekBar.max = 255
        bSeekBar.max = 255
        val tvColor = tv.textColors.defaultColor
        val r = Color.red(tvColor)
        val g = Color.green(tvColor)
        val b = Color.blue(tvColor)
        rSeekBar.progress = r
        gSeekBar.progress = g
        bSeekBar.progress = b
        tv_color.setTextColor(tvColor)

        val size = tv.textSize.toInt()
        sizeSeekBar.max = dp2px(100)
        sizeSeekBar.progress = size
        tv_size.text = "文字大小: (${size})"

        val rotation = tv.rotation.toInt()
        rotationSeekBar.max = 359
        rotationSeekBar.progress = rotation
        tv_rotation.text = "文字旋转角度: (${rotation}°)"
    }

}