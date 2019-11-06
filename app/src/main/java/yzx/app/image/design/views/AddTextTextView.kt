package yzx.app.image.design.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class AddTextTextView : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val pd = paddingLeft + paddingRight
        val textLength = paint.measureText(text.toString())
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec((pd + textLength).toInt(), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
        )
    }

}