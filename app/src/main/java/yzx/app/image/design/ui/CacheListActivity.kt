package yzx.app.image.design.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
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
import yzx.app.image.design.utils.*
import yzx.app.image.design.views.SlideMenuLayout
import java.io.File


private val callbackList = HashMap<String, (String) -> Unit>()
val keyEvent_ImageSelected = "k_cache_image_selected"

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
        if (savedInstanceState != null) {
            finish()
            return
        }
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
            findViewById<View>(R.id.image).setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    hideMenuIfExistsOpen(v)
                    event.action = MotionEvent.ACTION_CANCEL
                }
                false
            }
        }) {}

        fun bindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val file = files[position]
            val image = holder.itemView.findViewById<ImageView>(R.id.image)
            Glide.with(image).load(file).into(image)
            image.setOnClickListener { onItemClick(file.absolutePath, holder) }
            image.setOnLongClickListener { onLongClick(file.absolutePath, holder); true }
            holder.itemView.findViewById<View>(R.id.menuLayout).setOnClickListener { deleteItem(file, files) }
            (holder.itemView as SlideMenuLayout).hide(false)
        }

        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun getItemCount(): Int = files.size
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createItem(parent)
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = bindViewHolder(holder, position)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) = hideMenuIfExistsOpen(null)
        })
    }


    private fun hideMenuIfExistsOpen(self: View?) {
        val cc = recyclerView.childCount
        for (i in 0 until cc) {
            val child = recyclerView.getChildAt(i)
            if (child.findViewById<View>(R.id.image) != self)
                (child as? SlideMenuLayout)?.hide()
        }
    }

    private fun deleteItem(file: File, files: List<File>) {
        val position = files.indexOf(file)
        file.delete()
        (files as MutableList).remove(file)
        recyclerView.adapter?.notifyItemRemoved(position)
    }

    private fun onItemClick(path: String, holder: RecyclerView.ViewHolder) {
        cancelListenEvent(keyEvent_ImageSelected)
        CacheImageSelectedActivity.launch(key, path)
        listenEvent(keyEvent_ImageSelected, object : EventCallback {
            override fun onEvent(what: String, data: Any?) {
                callbackList.remove(key)?.invoke(path)
                finish()
            }
        })
    }

    private fun onLongClick(path: String, holder: RecyclerView.ViewHolder) {
        (holder.itemView as SlideMenuLayout).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        cancelListenEvent(keyEvent_ImageSelected)
        callbackList.remove(key)
    }

}