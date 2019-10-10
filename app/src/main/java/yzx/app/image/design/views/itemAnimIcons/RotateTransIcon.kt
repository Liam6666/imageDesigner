package yzx.app.image.design.views.itemAnimIcons

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import yzx.app.image.design.R
import yzx.app.image.design.utils.dp2px

class RotateTransIcon : ImageView, AnimIconAble {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        val padding = dp2px(4)
        setPadding(padding, padding, padding, padding)
        scaleType = ScaleType.FIT_CENTER
        setImageResource(R.drawable.main_list_icon_1)
    }


    override fun doAnim() {
        rotation = 0f
        animate().rotation(360f).setDuration(400).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(p0: Animator?) {
                rotationY = 0f
                animate().rotationY(360f * 2).setDuration(800).setListener(null).start()
            }
        }).start()
    }

}