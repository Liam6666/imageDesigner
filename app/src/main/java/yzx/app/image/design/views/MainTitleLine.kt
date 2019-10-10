package yzx.app.image.design.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class MainTitleLine : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = Color.parseColor("#00ff88")
    }


    override fun onDraw(canvas: Canvas) {

        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = height.toFloat()

        val gap = height * 3
        canvas.drawLine(height / 2f, height / 2f, (width - gap).toFloat(), height / 2f, paint)

        paint.style = Paint.Style.FILL
        canvas.drawCircle(width - height / 2f, height / 2f, height / 2f, paint)

    }

}