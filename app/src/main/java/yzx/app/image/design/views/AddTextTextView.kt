package yzx.app.image.design.views

import android.content.Context
import android.graphics.Color
import android.text.Layout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class AddTextTextView : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        setBackgroundColor(Color.parseColor("#abcdef"))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val pdLR = paddingLeft + paddingRight
        val textWidth = Layout.getDesiredWidth(text, paint)

        val pdTB = paddingTop + paddingBottom
        val currentLineCount = layout?.lineCount ?: 0
        if (currentLineCount > 0)
            originLineCount = currentLineCount
        val textHeight = (paint.fontMetrics.descent - paint.fontMetrics.ascent) * originLineCount

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec((pdLR + textWidth).toInt(), MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec((pdTB + textHeight).toInt(), MeasureSpec.EXACTLY)
        )
    }


    private var originLineCount = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        pivotX = w / 2f
        pivotY = h / 2f
    }

}