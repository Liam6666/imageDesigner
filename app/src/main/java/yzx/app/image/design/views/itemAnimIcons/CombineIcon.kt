package yzx.app.image.design.views.itemAnimIcons

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_main_list_icon_combine.view.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.inflateView


class CombineIcon : FrameLayout, AnimIconAble {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    init {
        inflateView(context, R.layout.layout_main_list_icon_combine, this, true)
    }


    private val views = arrayOf(block1, block2, block3, block4)


    override fun doAnim() {
        val perDuration = 180
        views.forEachIndexed { index, view ->
            val hideDelay = index * perDuration
            view.animate().scaleX(0f).scaleY(0f).setDuration(perDuration.toLong()).setStartDelay(hideDelay.toLong()).start()
        }
        postDelayed({
            views.forEachIndexed { index, view ->
                val showDelay = index * perDuration
                view.animate().scaleX(1f).scaleY(1f).setDuration(perDuration.toLong()).setStartDelay(showDelay.toLong()).start()
            }
        }, (views.size * perDuration).toLong())
    }


}