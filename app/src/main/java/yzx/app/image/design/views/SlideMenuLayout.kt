package yzx.app.image.design.views

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import yzx.app.image.design.R
import yzx.app.image.design.utils.application
import yzx.app.image.design.utils.cancel2
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.singletonLinearInterpolator
import kotlin.math.abs


class SlideMenuLayout(context: Context, val attrs: AttributeSet?) : FrameLayout(context, attrs) {


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
        if (isMenuOpen || menuView == null || menuView!!.width <= 0)
            return
        if (!anim) {
            cancelAnim()
            scrollTo(menuView!!.width, 0)
        } else {
            animToTargetState(true)
        }
        isMenuOpen = true
    }


    fun hide(anim: Boolean = true) {
        if (!isMenuOpen || menuView == null || menuView!!.width <= 0)
            return
        if (!anim) {
            cancelAnim()
            scrollTo(0, 0)
        } else {
            animToTargetState(false)
        }
        isMenuOpen = false
    }


    //
    //


    private val downPoint = PointF()
    private val lastPoint = PointF()
    private val touchSlop = ViewConfiguration.get(application).scaledTouchSlop
    private var hasIntercepted = false


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (anim != null && anim!!.isRunning)
            return super.dispatchTouchEvent(ev)
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
        if (anim != null && anim!!.isRunning)
            return super.onInterceptTouchEvent(ev)
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
                onUpOrCancel()
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (anim != null && anim!!.isRunning)
            return super.onTouchEvent(ev)
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
                onUpOrCancel()
            }
        }
        return true
    }


    //
    //


    private var toggleStageGap = dp2px(20)
    private var isMenuOpen = false
    private var anim: ValueAnimator? = null

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SlideMenuLayout)
        toggleStageGap = ta.getDimension(R.styleable.SlideMenuLayout_sml_toggleStateGap, toggleStageGap.toFloat()).toInt()
        ta.recycle()
    }

    private fun onUpOrCancel() {
        cancelAnim()
        menuView?.run {
            if (this@SlideMenuLayout.scrollX == menuView!!.width) {
                isMenuOpen = true
            } else if (this@SlideMenuLayout.scrollX == 0) {
                isMenuOpen = false
            } else {
                val targetOpen = if (isMenuOpen) {
                    this@SlideMenuLayout.scrollX >= menuView!!.width - toggleStageGap
                } else {
                    this@SlideMenuLayout.scrollX > toggleStageGap
                }
                isMenuOpen = targetOpen
                animToTargetState(targetOpen)
            }
        }
    }


    private fun animToTargetState(targetOpen: Boolean) {
        anim = ValueAnimator.ofInt(scrollX, if (targetOpen) menuView!!.width else 0).setDuration(150).apply {
            interpolator = singletonLinearInterpolator
            addUpdateListener { scrollTo(it.animatedValue as Int, 0) }
            start()
        }
    }

    private fun cancelAnim() {
        anim.cancel2()
        anim = null
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