package yzx.app.image.design.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yzx.app.image.design.R
import yzx.app.image.design.utils.launchActivity


private val callbackList = HashMap<String, (String) -> Unit>()


class CacheListActivity : AppCompatActivity() {

    companion object {
        fun launch(activity: Activity, onSelected: (path: String) -> Unit) {
            val key = System.currentTimeMillis().toString()
            callbackList.put(key, onSelected)
            activity.launchActivity<CacheListActivity>("key" to key)
        }
    }


    private val key: String by lazy { intent.getStringExtra("key") ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (key.isEmpty()) {
            finish()
            return
        }
        setContentView(R.layout.activity_cache_list)

    }


    override fun onDestroy() {
        super.onDestroy()
        callbackList.remove(key)
    }

}