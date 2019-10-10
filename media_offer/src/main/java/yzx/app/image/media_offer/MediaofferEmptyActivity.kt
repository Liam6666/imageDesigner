package yzx.app.image.media_offer

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.UriUtils
import yzx.app.image.design.utils.launchActivity
import yzx.app.image.permissionutil.PermissionRequester
import java.io.File


class MediaOfferEmptyActivity : AppCompatActivity() {


    companion object {
        fun launch(context: Context, sign: String, type: String) = context.launchActivity<MediaOfferEmptyActivity>("s" to sign, "t" to type)
    }


    private val sign: String? by lazy { intent?.getStringExtra("s") }
    private val type: String? by lazy { intent?.getStringExtra("t") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (sign.isNullOrEmpty() || type.isNullOrEmpty())
            doOver(null)
        else
            PermissionRequester.request(this, Manifest.permission.READ_EXTERNAL_STORAGE) { result ->
                if (!result) doOver(null) else start()
            }
    }


    private fun start() {
        when (type) {
            MediaOffer.type_image -> {
                val chooser = Intent(Intent.ACTION_CHOOSER)
                chooser.putExtra(Intent.EXTRA_TITLE, "请选择图片")
                chooser.putExtra(Intent.EXTRA_INTENT, Intent(Intent.ACTION_PICK, null).apply {
                    setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                })
                startActivityForResult(chooser, 6667)
            }
            else -> {
                doOver(null)
            }
        }
    }

    private fun doOver(path: File?) {
        MediaOffer.callbacks.remove(sign)?.invoke(path)
        finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 6667) {
            val uri = data?.data
            if (uri == null) {
                doOver(null)
            } else {
                doOver(UriUtils.uri2File(uri))
            }
        } else {
            doOver(null)
        }
    }


    override fun onBackPressed() = Unit

}