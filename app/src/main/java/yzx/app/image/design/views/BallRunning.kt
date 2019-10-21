package yzx.app.image.design.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import yzx.app.image.design.R
import yzx.app.image.design.utils.cancel2

class BallRunning : View {

    private var radius: Float = 0f
    private var color: Int = 0
    private var duration = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.BallRunning)
        radius = ta.getDimension(R.styleable.BallRunning_brun_radius, 0f)
        color = ta.getColor(R.styleable.BallRunning_brun_color, 0)
        duration = ta.getInt(R.styleable.BallRunning_brun_duration, 0)
        ta.recycle()
    }


    init {
        addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) = cancel()
            override fun onViewAttachedToWindow(v: View?) = start()
        })
    }


    private var anim: ValueAnimator? = null

    private fun start() {
        cancel()
        if (duration == 0 || color == 0 || radius == 0f)
            return
        progress = 0f
        anim = ValueAnimator.ofFloat(0f, 1f).setDuration(duration.toLong()).apply {
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                progress = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    private fun cancel() {
        anim.cancel2()
        anim = null
    }


    private var progress: Float = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onDraw(canvas: Canvas) {
        paint.color = color
        paint.style = Paint.Style.FILL

        val cy = height / 2f
        val cx = (width - (2f * radius)) * progress + radius

        canvas.drawCircle(cx, cy, radius, paint)
    }


}