package yzx.app.image.design.logicViews

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import yzx.app.image.design.R
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.inflateView


object MainListHelper {


    fun makeItem(activity: Activity, recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)

        class Holder(view: View) : RecyclerView.ViewHolder(view)
        recyclerView.adapter = object : RecyclerView.Adapter<Holder>() {
            override fun getItemCount(): Int = itemsList.size
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val view = inflateView(activity, R.layout.item_layout_main_list, parent)
                view.layoutParams.width = (ScreenUtils.getAppScreenWidth() - dp2px(40)) / 2
                view.layoutParams.height = view.layoutParams.width
                return Holder(view)
            }

            override fun onBindViewHolder(holder: Holder, position: Int) {
                holder.itemView.findViewById<ImageView>(R.id.image).setImageResource(itemsList[position].icon!!)
                holder.itemView.findViewById<TextView>(R.id.text).text = itemsList[position].text
                holder.itemView.findViewById<View>(R.id.click).setOnClickListener { itemsList[position].click!!.run() }
            }
        }
    }


    private class ItemInfo {
        var icon: Int? = null
        var text: String? = null
        var click: Runnable? = null
    }


    private val itemsList = arrayOf(
        ItemInfo().apply {
            icon = R.mipmap.ic_launcher
            text = "旋转偏移"
            click = Runnable { }
        },
        ItemInfo().apply {
            icon = R.mipmap.ic_launcher
            text = "加文字"
            click = Runnable { }
        },
        ItemInfo().apply {
            icon = R.mipmap.ic_launcher
            text = "效果滤镜"
            click = Runnable { }
        },
        ItemInfo().apply {
            icon = R.mipmap.ic_launcher
            text = "画图"
            click = Runnable { }
        },
        ItemInfo().apply {
            icon = R.mipmap.ic_launcher
            text = "裁剪"
            click = Runnable { }
        },
        ItemInfo().apply {
            icon = R.mipmap.ic_launcher
            text = "拼接合并"
            click = Runnable { }
        },
        ItemInfo().apply {
            icon = R.mipmap.ic_launcher
            text = "区域抠取"
            click = Runnable { }
        }
    )

}