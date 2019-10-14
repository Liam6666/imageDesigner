package yzx.app.image.design.logicViews

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import yzx.app.image.design.R
import yzx.app.image.design.utils.inflateView
import java.util.*


private val showInfoList = WeakHashMap<Activity, Dialog>()


fun Activity.showWaitingDialog(cancelAble: Boolean = false) {
    if (isFinishing || isDestroyed)
        return
    var dialog = showInfoList.get(this)
    if (dialog == null) {
        dialog = makeDialog(this)
        showInfoList.put(this, dialog)
    }
    dialog.setCanceledOnTouchOutside(false)
    dialog.setCancelable(cancelAble)
    dialog.show()
}


fun Activity.dismissWaitingDialog() {
    if (isFinishing || isDestroyed) return
    try {
        showInfoList.remove(this)?.dismiss()
    } catch (e: Exception) {
    }
}


private fun makeDialog(activity: Activity): Dialog {
    val view = inflateView(activity, R.layout.layout_dialog_waiting, activity.window.decorView as ViewGroup, false)
    return AlertDialog.Builder(activity).run {
        create().apply {
            show()
            setContentView(view)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.attributes?.width = view.layoutParams.width
            window?.attributes?.height = view.layoutParams.height
            window?.attributes = window?.attributes
        }
    }
}