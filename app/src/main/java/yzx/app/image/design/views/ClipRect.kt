package yzx.app.image.design.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.getRealImageWidthAndHeight


class ClipRect : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    // 可裁剪的最小宽度(按比例值, 非真实值)
    private val minClipLength = dp2px(60) + 1f
    // 四个角的可响应触摸的正方形的边长
    private val touchRectLength = minClipLength / 2f


    // ImageView中的图片的宽度
    private var imageWidth = 0
    // ImageView中的图片的高度
    private var imageHeight = 0
    // ImageView中的图片的左边界
    private var imageLeftBoundary = 0f
    // ImageView中的图片的右边界
    private var imageRightBoundary = 0f
    // ImageView中的图片的上边界
    private var imageTopBoundary = 0f
    // ImageView中的图片的下边界
    private var imageBottomBoundary = 0f


    // 当前圈选区域左边界
    private var c_left = 0f
    // 当前圈选区域右边界
    private var c_right = minClipLength
    // 当前圈选区域上边界
    private var c_top = 0f
    // 当前圈选区域下边界
    private var c_bottom = minClipLength


    /**
     * 设置对应的ImageView, 将可选区域设置成同ImageView的显示图片一致
     */
    fun bindImageView(iv: ImageView) {
        iv.getRealImageWidthAndHeight { result ->
            check(result.x > 0 && result.y > 0) { "please set image first" }
            post {
                imageWidth = result.x
                imageHeight = result.y
                c_left = width / 2f - imageWidth / 2f
                c_right = c_left + imageWidth
                c_top = height / 2f - imageHeight / 2f
                c_bottom = c_top + imageHeight
                imageLeftBoundary = c_left
                imageRightBoundary = c_right
                imageTopBoundary = c_top
                imageBottomBoundary = c_bottom
                invalidate()
            }
        }
    }


    private val cornerLineColor = Color.parseColor("#ffffff")
    private val cornerLineStrokeWidth = dp2px(3)
    private val darkColor = Color.parseColor("#aa000000")
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipPath = Path()
    private val darkPath = Path()


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> handleDown(event)
            MotionEvent.ACTION_MOVE -> handleMove(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> handleOver(event)
        }
        return true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        clipPath.reset()
        clipPath.addRect(c_left, c_top, c_right, c_bottom, Path.Direction.CW)
        canvas.drawPath(clipPath, paint.apply {
            color = Color.TRANSPARENT
        })

        darkPath.reset()
        darkPath.addRect(0f, 0f, width.toFloat(), c_top, Path.Direction.CW)
        darkPath.addRect(0f, 0f, c_left, height.toFloat(), Path.Direction.CW)
        darkPath.addRect(0f, c_bottom, width.toFloat(), height.toFloat(), Path.Direction.CW)
        darkPath.addRect(c_right, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)
        canvas.drawPath(darkPath, paint.apply {
            color = darkColor
        })

        paint.strokeWidth = cornerLineStrokeWidth.toFloat()
        paint.color = cornerLineColor
        paint.style = Paint.Style.FILL
        canvas.drawLine(c_left, c_top + cornerLineStrokeWidth / 2f, c_left + touchRectLength, c_top + cornerLineStrokeWidth / 2f, paint)
        canvas.drawLine(c_left + cornerLineStrokeWidth / 2, c_top, c_left + cornerLineStrokeWidth / 2, c_top + touchRectLength, paint)
        canvas.drawLine(c_right, c_top + cornerLineStrokeWidth / 2, c_right - touchRectLength, c_top + cornerLineStrokeWidth / 2, paint)
        canvas.drawLine(c_right - cornerLineStrokeWidth / 2, c_top, c_right - cornerLineStrokeWidth / 2, c_top + touchRectLength, paint)
        canvas.drawLine(c_left + cornerLineStrokeWidth / 2, c_bottom, c_left + cornerLineStrokeWidth / 2, c_bottom - touchRectLength, paint)
        canvas.drawLine(c_left, c_bottom - cornerLineStrokeWidth / 2, c_left + touchRectLength, c_bottom - cornerLineStrokeWidth / 2, paint)
        canvas.drawLine(c_right - cornerLineStrokeWidth / 2, c_bottom, c_right - cornerLineStrokeWidth / 2, c_bottom - touchRectLength, paint)
        canvas.drawLine(c_right, c_bottom - cornerLineStrokeWidth / 2, c_right - touchRectLength, c_bottom - cornerLineStrokeWidth / 2, paint)
    }


    private var currentTouchPosition: DownPosition? = null
    private val lastPosition = PointF(0f, 0f)


    private fun handleDown(event: MotionEvent) {
        currentTouchPosition = getDownPosition(event.x, event.y)
        lastPosition.x = event.rawX
        lastPosition.y = event.rawY
    }


    private fun handleMove(event: MotionEvent) {
        val xGap = event.rawX - lastPosition.x
        val yGap = event.rawY - lastPosition.y
        lastPosition.x = event.rawX
        lastPosition.y = event.rawY
        when (currentTouchPosition) {
            DownPosition.LT -> {
                c_left += xGap
                c_top += yGap
                if (c_left < imageLeftBoundary) c_left = imageLeftBoundary
                if (c_left + minClipLength > c_right) c_left = c_right - minClipLength
                if (c_top < imageTopBoundary) c_top = imageTopBoundary
                if (c_top + minClipLength > c_bottom) c_top = c_bottom - minClipLength
                invalidate()
            }
            DownPosition.RT -> {
                c_right += xGap
                c_top += yGap
                if (c_right > imageRightBoundary) c_right = imageRightBoundary
                if (c_right - minClipLength < c_left) c_right = c_left + minClipLength
                if (c_top < imageTopBoundary) c_top = imageTopBoundary
                if (c_top + minClipLength > c_bottom) c_top = c_bottom - minClipLength
                invalidate()
            }
            DownPosition.LB -> {
                c_left += xGap
                c_bottom += yGap
                if (c_left < imageLeftBoundary) c_left = imageLeftBoundary
                if (c_left + minClipLength > c_right) c_left = c_right - minClipLength
                if (c_bottom > imageBottomBoundary) c_bottom = imageBottomBoundary
                if (c_bottom - minClipLength < c_top) c_bottom = c_top + minClipLength
                invalidate()
            }
            DownPosition.RB -> {
                c_right += xGap
                c_bottom += yGap
                if (c_right > imageRightBoundary) c_right = imageRightBoundary
                if (c_right - minClipLength < c_left) c_right = c_left + minClipLength
                if (c_bottom > imageBottomBoundary) c_bottom = imageBottomBoundary
                if (c_bottom - minClipLength < c_top) c_bottom = c_top + minClipLength
                invalidate()
            }
        }
    }

    private fun handleOver(event: MotionEvent) {
        currentTouchPosition = null
    }


    /* 获取手指点击位置 */
    private fun getDownPosition(x: Float, y: Float): DownPosition? {
        if (x > c_left - touchRectLength && x < c_left + touchRectLength && y > c_top - touchRectLength && y < c_top + touchRectLength)
            return DownPosition.LT
        if (x > c_right - touchRectLength && x < c_right + touchRectLength && y > c_top - touchRectLength && y < c_top + touchRectLength)
            return DownPosition.RT
        if (x > c_left - touchRectLength && x < c_left + touchRectLength && y > c_bottom - touchRectLength && y < c_bottom + touchRectLength)
            return DownPosition.LB
        if (x > c_right - touchRectLength && x < c_right + touchRectLength && y > c_bottom - touchRectLength && y < c_bottom + touchRectLength)
            return DownPosition.RB
        return null
    }


    enum class DownPosition {
        LT, RT, LB, RB
    }

}