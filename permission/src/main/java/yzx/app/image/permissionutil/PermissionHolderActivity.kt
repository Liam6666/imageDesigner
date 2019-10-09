package yzx.app.image.permissionutil

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import yzx.app.image.design.utils.launchActivity
import yzx.app.image.design.utils.permissionGranted


class PermissionHolderActivity : AppCompatActivity() {


    companion object {
        fun launch(act: Activity, sign: String, p: String) = act.launchActivity<PermissionHolderActivity>("s" to sign, "p" to p)
    }


    private val sign: String? by lazy { intent?.getStringExtra("s") }
    private val permission: String? by lazy { intent?.getStringExtra("p") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sign.isNullOrEmpty() || permission.isNullOrEmpty()) {
            doOver(false)
        } else
            ActivityCompat.requestPermissions(this, arrayOf(permission), 7788)
    }


    private fun doOver(result: Boolean) {
        PermissionRequester.callbacks.remove(sign)?.invoke(result)
        finish()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 7788) {
            doOver(permissionGranted(permission!!))
        } else {
            doOver(false)
        }
    }


    override fun onBackPressed() = Unit

}