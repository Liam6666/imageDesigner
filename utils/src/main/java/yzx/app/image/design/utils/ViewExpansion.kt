package yzx.app.image.design.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.renderscript.Int2
import android.view.View
import android.view.WindowManager
import android.widget.ImageView


/**
 * 获取ImageView中图片显示的宽高
 */
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


/**
 * 弹出dialog
 */
fun Activity.showDialog(contentView: View, width: Int, height: Int, block: (Dialog) -> Unit) {
    if (isFinishing) return
    AlertDialog.Builder(this).create().apply {
        show()
        setContentView(contentView)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes?.width = width
        window?.attributes?.height = height
        window?.attributes = window?.attributes
        block.invoke(this)
    }
}


/**
 * dismiss dialog safe
 */
fun dismissDialog(dialog: Dialog?) {
    dialog?.runCatching { dismiss() }
}