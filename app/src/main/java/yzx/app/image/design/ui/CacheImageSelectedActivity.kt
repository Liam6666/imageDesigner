package yzx.app.image.design.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_cache_image_selected.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.launchActivity
import yzx.app.image.design.utils.sendEvent


class CacheImageSelectedActivity : AppCompatActivity() {

    companion object {
        fun launch(activity: Activity, file: String) {
            activity.launchActivity<CacheImageSelectedActivity>("path" to file)
        }
    }


    private val path: String by lazy { intent.getStringExtra("path") ?: "" }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            finish()
            return
        }
        if (path.isEmpty()) {
            finish()
            return
        }

        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_cache_image_selected)

        backShadow.setOnClickListener { finish() }

        Glide.with(this).load(path).into(image)

        confirm.setOnClickListener {
            sendEvent(keyEvent_ImageSelected)
            finish()
        }
    }

}