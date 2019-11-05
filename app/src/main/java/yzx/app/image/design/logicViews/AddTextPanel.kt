package yzx.app.image.design.logicViews

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_add_text_panel.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.inflateView


class AddTextPanel : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        inflateView(context, R.layout.layout_add_text_panel, this, true)
    }


    fun getCloseView() = close


    fun setTextView(tv: TextView) {


    }

}