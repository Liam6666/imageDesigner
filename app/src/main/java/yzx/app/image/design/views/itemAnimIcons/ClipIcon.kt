package yzx.app.image.design.views.itemAnimIcons

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import yzx.app.image.design.utils.dp2px


private val ic_color = Color.parseColor("#f708ca")

class ClipScissorsLeft(context: Context) : View(context) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = ic_color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dp2px(3).toFloat()

        val radius = dp2px(6).toFloat()
        canvas.drawLine(width.toFloat() - dp2px(2), dp2px(2).toFloat(), radius, height - radius, paint)

        paint.style = Paint.Style.FILL
        canvas.drawCircle(radius, height - radius, radius, paint)
        paint.color = Color.WHITE
        canvas.drawCircle(radius, height - radius, radius / 2f, paint)
    }
}

class ClipScissorsRight(context: Context) : View(context) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = ic_color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dp2px(3).toFloat()

        val radius = dp2px(6).toFloat()
        canvas.drawLine(0f + dp2px(2), 0f + dp2px(2), width - radius, height - radius, paint)

        paint.style = Paint.Style.FILL
        canvas.drawCircle(width - radius, height - radius, radius, paint)
        paint.color = Color.WHITE
        canvas.drawCircle(width - radius, height - radius, radius / 2f, paint)
    }
}

class ClipIcon : FrameLayout, AnimIconAble {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private val sub1: View
    private val sub2: View

    init {
        sub1 = ClipScissorsLeft(context)
        sub2 = ClipScissorsRight(context)
        addView(sub1, LayoutParams(dp2px(30), dp2px(30)).apply {
            gravity = Gravity.CENTER
        })
        addView(sub2, LayoutParams(dp2px(30), dp2px(30)).apply {
            gravity = Gravity.CENTER
        })
    }


    override fun doAnim() {
        val anim = ValueAnimator.ofFloat(0f, 1f).setDuration(250)
        anim.repeatMode = ValueAnimator.REVERSE
        anim.repeatCount = 7
        anim.addUpdateListener { va ->
            val p = va.animatedValue as Float
            val degree = 35 * p
            sub1.rotation = -degree
            sub2.rotation = degree
        }
        anim.start()
    }


}