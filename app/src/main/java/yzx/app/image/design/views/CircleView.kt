package yzx.app.image.design.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import yzx.app.image.design.R
import kotlin.math.min

class CircleView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleView)
        val color = ta.getColor(R.styleable.CircleView_cv_color, Color.BLACK)
        setColor(color)
        ta.recycle()
    }


    private var color = Color.BLACK


    fun setColor(color: Int) {
        this.color = color
        invalidate()
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = color

        canvas.save()
        canvas.translate(width / 2f, height / 2f)
        canvas.drawCircle(0f, 0f, min(width / 2f, height / 2f) - 1, paint)
        canvas.restore()
    }

}