package yzx.app.image.design.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat


lateinit var application: Application


inline fun <reified T : Activity> Context.launchActivity(vararg params: Pair<String, String>) {
    startActivity(Intent(this, T::class.java).apply {
        if (this !is Activity)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        params.forEach { putExtra(it.first, it.second) }
    })
}


fun permissionGranted(p: String) = ActivityCompat.checkSelfPermission(application, p) == PackageManager.PERMISSION_GRANTED


private var toast: Toast? = null
fun toast(str: String) {
    toast?.cancel()
    toast = Toast.makeText(application, str, Toast.LENGTH_SHORT)
    toast!!.show()
}


private var longToast: Toast? = null

fun longToast(str: String) {
    longToast?.cancel()
    longToast = Toast.makeText(application, str, Toast.LENGTH_LONG)
    longToast!!.show()
}