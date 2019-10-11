package yzx.app.image.design.logicViews

import android.app.Activity
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import yzx.app.image.design.R
import yzx.app.image.design.utils.application
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.inflateView
import yzx.app.image.design.views.itemAnimIcons.*


class MainListHelper {


    /**
     * make home page list
     */
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
                val info = itemsList[position]
                holder.itemView.findViewById<TextView>(R.id.text).text = info.text
                holder.itemView.findViewById<View>(R.id.click).setOnClickListener { info.click?.run() }
                val animViewContainer = holder.itemView.findViewById<FrameLayout>(R.id.imageContainer)
                info.animIcon?.run {
                    (parent as? ViewGroup)?.removeView(this)
                    animViewContainer.addView(this, FrameLayout.LayoutParams(-1, -1))
                }
            }
        }

        playAnimOneByOne()
    }


    /**
     * cancel home page list anim
     */
    fun cancel() {
        animTimer?.cancel()
        animTimer = null
    }


    private var animTimer: CountDownTimer? = null
    private var animIndex = 0

    /*
     * 开始挨个执行icon的小动画
     */
    private fun playAnimOneByOne() {
        if (animTimer != null)
            throw IllegalStateException("list animation has already begin")
        var first = true
        animTimer = object : CountDownTimer(Long.MAX_VALUE, 2000) {
            override fun onFinish() = Unit
            override fun onTick(p0: Long) {
                if (first) {
                    first = false
                } else {
                    val index = animIndex % itemsList.size
                    val info = itemsList[index]
                    if (info.animIcon is AnimIconAble)
                        (info.animIcon as AnimIconAble).doAnim()
                    animIndex++
                }
            }
        }
        animTimer!!.start()
    }


    /*
     * 首页列表数据单个item的bean
     */
    private class ItemInfo {
        var animIcon: View? = null
        var text: String? = null
        var click: Runnable? = null
    }


    /*
     * 首页列表数据
     */
    private val itemsList = arrayOf(
        ItemInfo().apply {
            animIcon = RotateTransIcon(application)
            text = "旋转偏移"
            click = Runnable { }
        },
        ItemInfo().apply {
            animIcon = AddTextIcon(application)
            text = "加文字"
            click = Runnable { }
        },
        ItemInfo().apply {
            animIcon = FilterIcon(application)
            text = "效果滤镜"
            click = Runnable { }
        },
        ItemInfo().apply {
            animIcon = DrawIcon(application)
            text = "画图"
            click = Runnable { }
        },
        ItemInfo().apply {
            animIcon = ClipIcon(application)
            text = "裁剪"
            click = Runnable { }
        },
        ItemInfo().apply {
            animIcon = RotateTransIcon(application)
            text = "拼接合并"
            click = Runnable { }
        },
        ItemInfo().apply {
            animIcon = RotateTransIcon(application)
            text = "区域抠取"
            click = Runnable { }
        }
    )

}