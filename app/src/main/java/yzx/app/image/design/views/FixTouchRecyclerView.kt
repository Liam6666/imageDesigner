package yzx.app.image.design.views

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import yzx.app.image.design.utils.application
import kotlin.math.abs

class FixTouchRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    private val downP = PointF()
    private val touchSlop = ViewConfiguration.get(application).scaledTouchSlop
    private var hTouch = false
    private var hasCheckTouch = false


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downP.x = ev.rawX
                downP.y = ev.rawY
                hTouch = false
                hasCheckTouch = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (!hasCheckTouch) {
                    val xd = abs(ev.rawX - downP.x)
                    val yd = abs(ev.rawY - downP.y)
                    if (xd > touchSlop || yd > touchSlop) {
                        hTouch = xd > yd
                        hasCheckTouch = true
                    }
                }
            }
        }
        return !hTouch && super.onInterceptTouchEvent(ev)
    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (hasCheckTouch && hTouch)
            return false
        return super.onTouchEvent(e)
    }

}