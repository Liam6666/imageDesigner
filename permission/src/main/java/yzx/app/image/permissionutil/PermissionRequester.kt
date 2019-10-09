package yzx.app.image.permissionutil

import android.app.Activity
import yzx.app.image.design.utils.permissionGranted


typealias PermissionCallback = (Boolean) -> Unit


object PermissionRequester {

    internal val callbacks = HashMap<String, PermissionCallback>()


    fun request(activity: Activity, p: String, cb: PermissionCallback) {
        if (permissionGranted(p)) {
            cb.invoke(true)
        } else {
            val signal = System.currentTimeMillis().toString()
            callbacks.put(signal, cb)
            PermissionHolderActivity.launch(activity, signal, p)
        }
    }


}