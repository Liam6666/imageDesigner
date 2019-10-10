package yzx.app.image.design.views.itemAnimIcons

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.animation.addListener
import kotlinx.android.synthetic.main.layout_main_list_icon_filter.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.inflateView


class FilterIcon : FrameLayout, AnimIconAble {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        inflateView(context, R.layout.layout_main_list_icon_filter, this, true)
    }


    override fun doAnim() {
        step1 { step2() }
    }


    private fun step1(end: () -> Unit) {
        val anim = ValueAnimator.ofFloat(0f, container.layoutParams.width / 2f).setDuration(400)
        anim.repeatMode = ValueAnimator.REVERSE
        anim.repeatCount = 1
        anim.addUpdateListener { va ->
            val p = va.animatedValue as Float
            c1.translationX = p
            c1.translationY = p
            c2.translationX = -p
            c2.translationY = p
            c3.translationX = p
            c3.translationY = -p
            c4.translationX = -p
            c4.translationY = -p
        }
        anim.addListener(onEnd = { end() })
        anim.start()
    }

    private fun step2() {
        if (!isAttachedToWindow)
            return
        val anim = ValueAnimator.ofFloat(0f, container.layoutParams.width / 2f).setDuration(400)
        anim.repeatMode = ValueAnimator.REVERSE
        anim.repeatCount = 1
        anim.addUpdateListener { va ->
            val p = va.animatedValue as Float
            c1.translationX = p
            c2.translationX = -p
            c3.translationX = p
            c4.translationX = -p
        }
        anim.start()
    }

}