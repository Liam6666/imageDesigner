package yzx.app.image.design.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_cache_image_selected.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.appEvent


class CacheImageSelectedActivity : AppCompatActivity() {

    companion object {
        fun launch(image: View, transName: String, activity: Activity, file: String) {
            val op = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, image, transName)
            ActivityCompat.startActivity(activity, Intent(activity, CacheImageSelectedActivity::class.java).apply {
                putExtra("path", file)
                putExtra("tname", transName)
            }, op.toBundle())
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

        backShadow.setOnClickListener { onBackPressed() }

        ViewCompat.setTransitionName(image, intent.getStringExtra("tname") ?: "")
        Glide.with(this).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(image)

        confirm.setOnClickListener {
            appEvent.postAppEvent(keyEvent_ImageSelected)
            finish()
        }
    }

}