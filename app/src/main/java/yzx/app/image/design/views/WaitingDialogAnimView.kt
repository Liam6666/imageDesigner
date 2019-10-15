package yzx.app.image.design.views

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_waiting_dialog.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.cancel2
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.inflateView


class WaitingDialogAnimView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        inflateView(context, R.layout.layout_waiting_dialog, this, true)
        addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) = cancel()
            override fun onViewAttachedToWindow(v: View?) = start()
        })
    }


    private var anim1: ValueAnimator? = null
    private var anim2: ValueAnimator? = null
    private var anim3: ValueAnimator? = null
    private var anim4: ValueAnimator? = null

    private fun start() {
        cancel()
        val len = dp2px(40 - 15).toFloat()
        anim1 = ValueAnimator.ofFloat(0f, len).setDuration(300).apply {
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { c1.translationY = it.animatedValue as Float }
            start()
        }
        anim2 = ValueAnimator.ofFloat(0f, len).setDuration(300).apply {
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                c2.translationY = -(it.animatedValue as Float)
                c2.translationX = -(it.animatedValue as Float)
            }
            start()
        }
        anim3 = ValueAnimator.ofFloat(0f, len).setDuration(300).apply {
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                c3.translationY = -(it.animatedValue as Float)
                c3.translationX = it.animatedValue as Float
            }
            start()
        }
        container.pivotX = container.layoutParams.width / 2f
        container.pivotY = container.layoutParams.height / 2f
        anim4 = ValueAnimator.ofFloat(0f, 360f).setDuration(400).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { container.rotation = it.animatedValue as Float }
            start()
        }
    }


    private fun cancel() {
        animate().cancel()
        anim1.cancel2()
        anim2.cancel2()
        anim3.cancel2()
        anim4.cancel2()
        anim1 = null
        anim2 = null
        anim3 = null
        anim4 = null
    }


}