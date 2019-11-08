package yzx.app.image.design.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_image_filter.*
import kotlinx.android.synthetic.main.item_filter_list.view.*
import yzx.app.image.design.R
import yzx.app.image.design.util.ImageFilters
import yzx.app.image.design.utils.decodeFileBitmapWithMaxLength
import yzx.app.image.design.utils.dp2px
import yzx.app.image.design.utils.inflateView
import yzx.app.image.design.utils.launchActivity


class ImageFilterActivity : AppCompatActivity(), IImageDesignActivity {

    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<ImageFilterActivity>("f" to path)
    }


    private val filePath by lazy { intent?.getStringExtra("f") ?: "" }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isEmpty()) {
            finish(); return
        }
        makeStatusBar()
        setContentView(R.layout.activity_image_filter)
        decodeFileBitmapWithMaxLength(this, filePath, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
            if (originBitmap == null) onBitmapLoadedError() else makeUI(originBitmap)
        }
    }


    private fun makeUI(originBitmap: Bitmap) {
        image.setImageBitmap(originBitmap)
        makeList()
        cache.setOnClickListener { }
        save.setOnClickListener { }
    }


    private fun makeList() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.right = dp2px(10)
                outRect.bottom = outRect.right
            }
        })
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            var selectedPosition = 0
            override fun getItemCount(): Int = ImageFilters.totalFilterCount
            override fun onCreateViewHolder(p: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = inflateView(p.context, R.layout.item_filter_list, p)
                view.layoutParams.width = (resources.displayMetrics.widthPixels - dp2px(24) - dp2px(10) * 5) / 5
                view.layoutParams.height = view.layoutParams.width
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                if (position == 0) {
                    holder.itemView.image.visibility = View.GONE
                    holder.itemView.defaultImage.visibility = View.VISIBLE
                    if (selectedPosition == 0) {
                        holder.itemView.number.visibility = View.VISIBLE
                        holder.itemView.number.text = ""
                        holder.itemView.number.background = ColorDrawable(Color.parseColor("#66ffffff"))
                    } else
                        holder.itemView.number.visibility = View.GONE
                } else {
                    holder.itemView.image.visibility = View.VISIBLE
                    holder.itemView.number.visibility = View.VISIBLE
                    holder.itemView.defaultImage.visibility = View.GONE
                    holder.itemView.number.text = position.toString()
                    if (selectedPosition == position) {
                        holder.itemView.number.background = ColorDrawable(Color.parseColor("#66ffffff"))
                    } else
                        holder.itemView.number.background = ColorDrawable(Color.parseColor("#66000000"))
                }
                holder.itemView.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                    
                }
            }
        }
    }

}