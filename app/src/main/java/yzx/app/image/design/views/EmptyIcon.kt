package yzx.app.image.design.views

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_empty.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.cancel2
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.inflateView
import yzx.app.image.design.utils.singletonLinearInterpolator


class EmptyIcon : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        inflateView(context, R.layout.layout_empty, this, true)
    }


    @Volatile
    private var isRunning = false
    private var anim: ValueAnimator? = null
    private var treeAnim: ValueAnimator? = null

    fun start() {
        stop()
        isRunning = true
        anim = ValueAnimator.ofFloat(0f, -dp2px(6).toFloat()).setDuration(600).apply {
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { plane.translationY = it.animatedValue as Float }
            start()
        }
        val roadWidth = dp2px(200)
        treeLayout.scrollTo(-roadWidth, 0)
        treeAnim = ValueAnimator.ofInt(-roadWidth, roadWidth * 2).setDuration(9000).apply {
            interpolator = singletonLinearInterpolator
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener { treeLayout.scrollTo(it.animatedValue as Int, 0) }
            start()
        }
    }


    fun stop() {
        isRunning = false
        anim.cancel2()
        anim = null
        treeAnim.cancel2()
        treeAnim = null
    }

}