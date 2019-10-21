package yzx.app.image.design.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_cache_list.*
import yzx.app.image.design.R
import yzx.app.image.design.logicViews.ImageCacheMgr
import yzx.app.image.design.utils.inflateView
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
        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_cache_list)

        val files = ImageCacheMgr.getAllCacheFiles()
        if (files.isEmpty()) {
            finish()
            return
        }
        back.setOnClickListener { finish() }

        val width = ScreenUtils.getAppScreenWidth() / 3
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun getItemCount(): Int = files.size
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                object : RecyclerView.ViewHolder(inflateView(parent.context, R.layout.item_cache_list, parent, false).apply {
                    layoutParams = ViewGroup.LayoutParams(width, width)
                }) {}

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val image = holder.itemView.findViewById<ImageView>(R.id.image)
                Glide.with(image).load(files[position]).into(image)
                image.setOnClickListener { onItemClick(files[position].absolutePath) }
            }
        }
    }


    private fun onItemClick(path: String) {

    }


    override fun onDestroy() {
        super.onDestroy()
        callbackList.remove(key)
    }

}