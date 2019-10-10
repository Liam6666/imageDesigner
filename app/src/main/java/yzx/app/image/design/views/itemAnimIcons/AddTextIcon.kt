package yzx.app.image.design.views.itemAnimIcons

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import yzx.app.image.design.R
import yzx.app.image.design.utils.dp2px

class AddTextIcon : ImageView, AnimIconAble {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        val padding = dp2px(5)
        setPadding(padding, padding, padding, padding)
        scaleType = ScaleType.FIT_CENTER
        setImageResource(R.drawable.main_list_icon_2)
    }


    override fun doAnim() {
        scaleX = 1f
        scaleY = 1f
        val anim = ValueAnimator.ofFloat(1f, 1.4f).setDuration(250)
        anim.addUpdateListener { va ->
            val p = va.animatedValue as Float
            scaleX = p
            scaleY = p
        }
        anim.repeatCount = 7
        anim.repeatMode = ValueAnimator.REVERSE
        anim.interpolator = DecelerateInterpolator()
        anim.start()
    }

}