package yzx.app.image.design.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_color_picker.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.inflateView
import kotlin.math.min


private const val gapAngle = 20f
private const val sweepAngle = (360f - (gapAngle * 3)) / 3f

private const val start1 = gapAngle / 2f + 1f
private const val end1 = start1 + sweepAngle
private const val start2 = end1 + gapAngle
private const val end2 = start2 + sweepAngle
private const val start3 = end2 + gapAngle
private const val end3 = start3 + sweepAngle


class ColorPickerButtonContainer(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val len = min(measuredWidth, measuredHeight)
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(len, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(len, MeasureSpec.EXACTLY)
        )
    }
}


class ColorPickerButton(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var color = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas) {
        canvas.translate(width / 2f, height / 2f)
        paint.style = Paint.Style.FILL
        paint.color = color
        canvas.drawCircle(0f, 0f, min(width, height) / 2f, paint)
    }
}


class ColorPickerBackground(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val redShader = SweepGradient(
        0f, 0f,
        intArrayOf(Color.parseColor("#000000"), Color.parseColor("#ff0000")),
        floatArrayOf(start1 / 360f, end1 / 360f)
    )
    private val greenShader = SweepGradient(
        0f, 0f,
        intArrayOf(Color.parseColor("#000000"), Color.parseColor("#00ff00")),
        floatArrayOf(start2 / 360f, end2 / 360f)
    )
    private val blueShader = SweepGradient(
        0f, 0f,
        intArrayOf(Color.parseColor("#000000"), Color.parseColor("#0000ff")),
        floatArrayOf(start3 / 360f, end3 / 360f)
    )

    private val rect = RectF()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /** 兼容按钮的padding */
    var padding = 0
        set(value) {
            field = value
            invalidate()
        }

    /** 中心颜色 */
    var showCenterColor = Color.WHITE
        set(value) {
            field = value
            invalidate()
        }

    override
    fun onDraw(canvas: Canvas) {
        val strokeWidth = min(width, height) / 22f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.strokeCap = Paint.Cap.ROUND

        canvas.translate(width / 2f, height / 2f)
        val halfStrokeWidth = strokeWidth / 2f
        rect.left = -width / 2f + halfStrokeWidth + padding
        rect.right = width / 2f - halfStrokeWidth - padding
        rect.top = -height / 2f + halfStrokeWidth + padding
        rect.bottom = height / 2f - halfStrokeWidth - padding

        paint.shader = redShader
        canvas.drawArc(rect, start1, sweepAngle, false, paint)

        paint.shader = greenShader
        canvas.drawArc(rect, start2, sweepAngle, false, paint)

        paint.shader = blueShader
        canvas.drawArc(rect, start3, sweepAngle, false, paint)

        paint.color = showCenterColor
        paint.style = Paint.Style.FILL
        paint.shader = null
        canvas.drawCircle(0f, 0f, min(width, height) / 5.5f, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val len = min(measuredWidth, measuredHeight)
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(len, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(len, MeasureSpec.EXACTLY)
        )
    }

}


////////////////////////////////////////////////////////////////////////////////// shit mother fuck //////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////// shit mother fuck //////////////////////////////////////////////////////////////////////


class ColorPicker : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        inflateView(context, R.layout.layout_color_picker, this, true)
        redButton.color = Color.RED
        greenButton.color = Color.GREEN
        blueButton.color = Color.BLUE
        setColor(Color.WHITE)
        post {
            makeRedButtonTouch()
            makeGreenButtonTouch()
            makeBlueButtonTouch()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val buttonLen = (min(width, height) / 8f).toInt()
        redButton.layoutParams.width = buttonLen
        redButton.layoutParams.height = buttonLen
        greenButton.layoutParams.width = buttonLen
        greenButton.layoutParams.height = buttonLen
        blueButton.layoutParams.width = buttonLen
        blueButton.layoutParams.height = buttonLen
        redButton.requestLayout()
        greenButton.requestLayout()
        blueButton.requestLayout()
        bgView.padding = ((buttonLen - bgView.paint.strokeWidth) / 2).toInt()
    }

    private fun makeRedButtonTouch() {
        val lastPoint = PointF()
        val unit = sweepAngle / min(width, height)
        redButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastPoint.x = event.rawX; lastPoint.y = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX
                    val nowY = event.rawY
                    val xGap = nowX - lastPoint.x
                    val yGap = nowY - lastPoint.y
                    lastPoint.x = nowX
                    lastPoint.y = nowY
                    val gap = if (getRedPercent() > 0.3f) -xGap else yGap
                    val degreeGap = gap * unit
                    var targetRotation = (redButton.parent as View).rotation + degreeGap
                    if (targetRotation < start1)
                        targetRotation = start1
                    else if (targetRotation > end1)
                        targetRotation = end1
                    (redButton.parent as View).rotation = targetRotation
                    notifyColorChangeByUser()
                }
            }
            true
        }
    }

    private fun makeGreenButtonTouch() {
        val lastPoint = PointF()
        val unit = sweepAngle / min(width, height)
        greenButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastPoint.y = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowY = event.rawY
                    val yGap = nowY - lastPoint.y
                    lastPoint.y = nowY
                    val degreeGap = -yGap * unit
                    var targetRotation = (greenButton.parent as View).rotation + degreeGap
                    if (targetRotation < start2)
                        targetRotation = start2
                    else if (targetRotation > end2)
                        targetRotation = end2
                    (greenButton.parent as View).rotation = targetRotation
                    notifyColorChangeByUser()
                }
            }
            true
        }
    }

    private fun makeBlueButtonTouch() {
        val lastPoint = PointF()
        val unit = sweepAngle / min(width, height)
        blueButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastPoint.x = event.rawX; lastPoint.y = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX
                    val nowY = event.rawY
                    val xGap = nowX - lastPoint.x
                    val yGap = nowY - lastPoint.y
                    lastPoint.x = nowX
                    lastPoint.y = nowY
                    val gap = if (getBluePercent() < 0.6f) xGap else yGap
                    val degreeGap = gap * unit
                    var targetRotation = (blueButton.parent as View).rotation + degreeGap
                    if (targetRotation < start3)
                        targetRotation = start3
                    else if (targetRotation > end3)
                        targetRotation = end3
                    (blueButton.parent as View).rotation = targetRotation
                    notifyColorChangeByUser()
                }
            }
            true
        }
    }


    private fun notifyColorChangeByUser() {
        getCurrentColor().run {
            bgView.showCenterColor = this
            onColorChanged?.invoke(this)
        }
    }


    //
    //


    /** 设置颜色 */
    fun setColor(color: Int) {
        bgView.showCenterColor = color
        setRedButtonPositionByColor(color)
        setGreenButtonPositionByColor(color)
        setBlueButtonPositionByColor(color)
        onColorChanged?.invoke(color)
    }

    /** 获取当前颜色 */
    fun getCurrentColor(): Int = Color.rgb(getRed(), getGreen(), getBlue())

    /** 颜色改变回调 */
    var onColorChanged: ((Int) -> Unit)? = null


    //
    //


    private fun setRedButtonPositionByColor(color: Int) {
        val red = Color.red(color).toFloat()
        val percent = red / 255f
        (redButton.parent as View).rotation = start1 + (sweepAngle * percent)
    }

    private fun setGreenButtonPositionByColor(color: Int) {
        val green = Color.green(color).toFloat()
        val percent = green / 255f
        (greenButton.parent as View).rotation = start2 + (sweepAngle * percent)
    }

    private fun setBlueButtonPositionByColor(color: Int) {
        val blue = Color.blue(color).toFloat()
        val percent = blue / 255f
        (blueButton.parent as View).rotation = start3 + (sweepAngle * percent)
    }

    private fun getRed(): Int = (getRedPercent() * 255f).toInt()
    private fun getGreen(): Int = (getGreenPercent() * 255f).toInt()
    private fun getBlue(): Int = (getBluePercent() * 255f).toInt()

    private fun getRedPercent(): Float = ((redButton.parent as View).rotation - start1) / sweepAngle
    private fun getGreenPercent(): Float = ((greenButton.parent as View).rotation - start2) / sweepAngle
    private fun getBluePercent(): Float = ((blueButton.parent as View).rotation - start3) / sweepAngle

}