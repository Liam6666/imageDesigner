package yzx.app.image.design.views.itemAnimIcons

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView

class RotateTransIcon : ImageView, AnimIconAble {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        setBackgroundColor(Color.RED)
    }


    override fun doAnim() {


    }

}