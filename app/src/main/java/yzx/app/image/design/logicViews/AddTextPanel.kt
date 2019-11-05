package yzx.app.image.design.logicViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
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
        setUIEvent()
    }

    /** 根据id获取内部任意View */
    fun getView(id: Int) = findViewById<View>(id)


    /* 设置UI事件 */
    private fun setUIEvent() {
        changeText.setOnClickListener { onChangeTextButtonClick?.invoke() }
        close.setOnClickListener { onCloseButtonClick?.invoke() }

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