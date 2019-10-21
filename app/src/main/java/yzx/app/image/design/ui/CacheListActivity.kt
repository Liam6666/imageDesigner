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

        val files = ImageCacheMgr.getAllCacheFiles()
        if (files.isEmpty()) {
            finish()
            return
        }

        window.statusBarColor = Color.BLACK
        setContentView(R.layout.activity_cache_list)
        back.setOnClickListener { finish() }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        fun createItem(parent: ViewGroup) = object : RecyclerView.ViewHolder(inflateView(parent.context, R.layout.item_cache_list, parent, false).apply {
            val width = ScreenUtils.getAppScreenWidth() / 3
            layoutParams = ViewGroup.LayoutParams(width, width)
        }) {}

        fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val file = files[position]
            val image = holder.itemView.findViewById<ImageView>(R.id.image)
            Glide.with(image).load(file).into(image)
            image.setOnClickListener { onItemClick(file.absolutePath, holder) }
            image.setOnLongClickListener { onLongClick(file.absolutePath, holder); true }
        }

        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun getItemCount(): Int = files.size
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createItem(parent)
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = bindViewHolder(holder, position)
        }
    }


    private fun onItemClick(path: String, holder: RecyclerView.ViewHolder) {

    }

    private fun onLongClick(path: String, holder: RecyclerView.ViewHolder) {

    }


    override fun onDestroy() {
        super.onDestroy()
        callbackList.remove(key)
    }

}