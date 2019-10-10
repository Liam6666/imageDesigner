package yzx.app.image.design.views.itemAnimIcons

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.layout_main_list_icon_draw.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.inflateView

class DrawIcon : FrameLayout, AnimIconAble {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        inflateView(context, R.layout.layout_main_list_icon_draw, this, true)
    }


    override fun doAnim() {

        val lip = LinearInterpolator()

        val imageAnim = ValueAnimator.ofFloat(0f, -dp2px(17).toFloat()).setDuration(400)
        imageAnim.interpolator = lip
        imageAnim.repeatCount = 3
        imageAnim.repeatMode = ValueAnimator.REVERSE
        imageAnim.addUpdateListener { va ->
            image.translationX = va.animatedValue as Float
        }
        imageAnim.start()

        ViewCompat.setPivotX(line1, 0f)
        ViewCompat.setPivotX(line2, 0f)
        line1.animate().scaleX(0f).setDuration(400).setInterpolator(lip).start()
        line2.animate().scaleX(0f).setDuration(400).setInterpolator(lip).start()


        postDelayed({ line1.animate().scaleX(1f).setDuration(400).setInterpolator(lip).start() }, 400)
        postDelayed({ line2.animate().scaleX(1f).setDuration(400).setInterpolator(lip).start() }, 1200)

    }

}