package yzx.app.image.design.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import yzx.app.image.design.utils.application
import kotlin.math.abs


class SlideMenuLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {


    private var contentView: View? = null
    private var menuView: View? = null


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 2)
            throw IllegalStateException("${javaClass.canonicalName} can only hold two child")
        contentView = getChildAt(0)?.apply {
            this.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            this.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
        menuView = getChildAt(1)?.apply {
            this.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        contentView?.layout(0, 0, measuredWidth, measuredHeight)
        menuView?.layout(measuredWidth, 0, measuredWidth + (menuView?.measuredWidth ?: 0), measuredHeight)
    }


    fun show(anim: Boolean = true) {

    }


    fun hide(anim: Boolean = false) {

    }

    //
    //


    private val downPoint = PointF()
    private val lastPoint = PointF()
    private val touchSlop = ViewConfiguration.get(application).scaledTouchSlop
    private var hasIntercepted = false


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downPoint.x = ev.rawX
                downPoint.y = ev.rawY
                lastPoint.x = ev.rawX
                lastPoint.y = ev.rawY
                hasIntercepted = false
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (hasIntercepted)
            return true
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                val nowX = ev.rawX
                val nowY = ev.rawY
                val xd = abs(nowX - downPoint.x)
                val yd = abs(nowY - downPoint.y)
                lastPoint.x = nowX
                lastPoint.y = nowY
                if (xd > touchSlop || yd > touchSlop) {
                    hasIntercepted = true
                    return true
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                val nowX = ev.rawX
                val nowY = ev.rawY
                val xd = nowX - lastPoint.x
                lastPoint.x = nowX
                lastPoint.y = nowY
                scrollBy(-xd.toInt(), 0)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

            }
        }
        return true
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(
            if (x < 0) {
                0
            } else {
                if (x > (menuView?.width ?: 0)) {
                    (menuView?.width ?: 0)
                } else {
                    x
                }
            }
            , y
        )
    }

}