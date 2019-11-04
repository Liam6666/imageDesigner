package yzx.app.image.design.utils

import android.renderscript.Int2
import android.widget.ImageView


fun ImageView.getRealImageWidthAndHeight(result: (Int2) -> Unit) {
    post {
        var x = 0
        var y = 0
        runCatching {
            val values = FloatArray(10)
            imageMatrix.getValues(values)
            val sx = values[0]
            val sy = values[4]
            x = (drawable.bounds.width() * sx).toInt()
            y = (drawable.bounds.height() * sy).toInt()
        }
        result.invoke(Int2().apply { this.x = x; this.y = y })
    }
}