package yzx.app.image.design.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView

class TextBoxLayer : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    fun add(text: String, color: Int) {
        TextView(context).apply {
            this.text = text
            setTextColor(color)
            textSize = 20f
            addView(this, LayoutParams(-2, -2).apply { gravity = Gravity.CENTER })
        }.post {

        }
    }


}