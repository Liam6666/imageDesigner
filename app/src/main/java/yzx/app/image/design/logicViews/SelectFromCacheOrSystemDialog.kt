package yzx.app.image.design.logicViews

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import yzx.app.image.design.R
import yzx.app.image.design.utils.inflateView


object SelectFromCacheOrSystemDialog {


    fun showIfNeeded(activity: Activity, callback: (fromCache: Boolean, fromSystem: Boolean) -> Unit) {
        if (ImageCacheMgr.existCacheFile()) {
            AlertDialog.Builder(activity).apply {
                create().apply {
                    setCancelable(true)
                    setCanceledOnTouchOutside(true)
                    show()

                    val view = inflateView(activity, R.layout.layout_dialog_select_cache_system, activity.window.decorView as ViewGroup, false)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setContentView(view)

                    view.findViewById<View>(R.id.fromCache).setOnClickListener {
                        dismiss()
                        callback.invoke(true, false)
                    }
                    view.findViewById<View>(R.id.fromSystem).setOnClickListener {
                        dismiss()
                        callback.invoke(false, true)
                    }
                }
            }
        } else {
            callback.invoke(false, true)
        }
    }

}