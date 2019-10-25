package yzx.app.image.design.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PaintView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    // 绘图bitmap
    var bitmap: Bitmap? = null
        set(value) {
            field = value
            canvas = Canvas(bitmap!!)
        }

    // 监听新一个画笔的创建
    var onNewPaintCreated: ((Paint) -> Unit)? = null


    fun back1() {
        if (paintInfoList.isEmpty())
            return
        paintInfoList.remove(paintInfoList.last())
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        if (paintInfoList.isNotEmpty())
            paintInfoList.forEach { canvas?.drawPath(it.path, it.paint) }
        invalidate()
    }

    fun clear() {
        if (paintInfoList.isEmpty())
            return
        paintInfoList.clear()
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }


    //
    //
    //


    class PaintInfo {
        val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val path = Path()
    }

    private val paintInfoList = ArrayList<PaintInfo>()


    private fun makeNewPaintInfo() {
        paintInfoList.add(PaintInfo().apply {
            onNewPaintCreated?.invoke(this.paint)
        })
    }


    private val lastPoint = PointF()
    private val nowPoint = PointF()
    private var canvas: Canvas? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (bitmap == null || canvas == null)
            return true
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastPoint.x = event.x
                lastPoint.y = event.y
                nowPoint.x = event.x
                nowPoint.y = event.y
                makeNewPaintInfo()
                paintInfoList.last().path.moveTo(nowPoint.x, nowPoint.y)
                paintInfoList.last().path.lineTo(nowPoint.x, nowPoint.y)
                canvas!!.drawPath(paintInfoList.last().path, paintInfoList.last().paint)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                nowPoint.x = event.x
                nowPoint.y = event.y
                paintInfoList.last().path.lineTo(nowPoint.x, nowPoint.y)
                canvas!!.drawPath(paintInfoList.last().path, paintInfoList.last().paint)
                lastPoint.x = event.x
                lastPoint.y = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                invalidate()
            }
        }
        return true
    }


    override fun onDraw(canvas: Canvas) {
        bitmap?.run { canvas.drawBitmap(this, 0f, 0f, null) }
    }

}