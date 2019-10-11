package yzx.app.image.design.views.itemAnimIcons

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import yzx.app.image.design.R
import yzx.app.image.design.utils.dp2px

class CutoutIcon : ImageView, AnimIconAble {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        setImageResource(R.drawable.main_list_icon_7)
        val padding = dp2px(8)
        setPadding(padding, padding, padding, padding)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (parent as? View)?.rotation = -45f
    }


    override fun doAnim() {
        val anim = ValueAnimator.ofFloat(0f, 360 * 5f).setDuration(350 * 4)
        anim.interpolator = LinearInterpolator()
        anim.addUpdateListener { rotationY = it.animatedValue as Float }
        anim.start()
    }


}