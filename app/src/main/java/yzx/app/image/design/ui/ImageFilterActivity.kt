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
import yzx.app.image.design.utils.*


class ImageFilterActivity : AppCompatActivity(), IImageDesignActivity {

    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<ImageFilterActivity>("f" to path)
    }


    private val filePath by lazy { intent?.getStringExtra("f") ?: "" }
    private var sourceBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isEmpty()) {
            finish(); return
        }
        makeStatusBar()
        setContentView(R.layout.activity_image_filter)
        decodeFileBitmapWithMaxLength(this, filePath, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
            sourceBitmap = originBitmap
            if (originBitmap == null) onBitmapLoadedError() else makeUI(originBitmap)
        }
    }


    private fun makeUI(originBitmap: Bitmap) {
        image.setImageBitmap(originBitmap)
        ImageFilters.makeEmptyBitmap(originBitmap) {
            toastMemoryError()
            finish()
        }
        if (!isFinishing) {
            makeList(originBitmap)
            cache.setOnClickListener { if (selectedPosition != 0) cacheBitmap(ImageFilters.getResult(originBitmap)) else toast("未选择滤镜") }
            save.setOnClickListener { if (selectedPosition != 0) startSaveBitmap(ImageFilters.getResult(originBitmap)) else toast("未选择滤镜") }
        }
    }


    private var selectedPosition = 0

    private fun makeList(originBitmap: Bitmap) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.right = dp2px(10)
                outRect.bottom = outRect.right
            }
        })
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun getItemCount(): Int = ImageFilters.filters.size + 1
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
                        holder.itemView.number.background = ColorDrawable(Color.parseColor("#66FFFFFF"))
                    } else
                        holder.itemView.number.visibility = View.GONE
                } else {
                    holder.itemView.image.visibility = View.VISIBLE
                    holder.itemView.number.visibility = View.VISIBLE
                    holder.itemView.defaultImage.visibility = View.GONE
                    holder.itemView.number.text = ImageFilters.filters[position - 1].name()
                    holder.itemView.number.background =
                        if (selectedPosition == position) ColorDrawable(Color.parseColor("#66FFFFFF"))
                        else ColorDrawable(Color.parseColor("#00000000"))
                    holder.itemView.image.setImageBitmap(ImageFilters.filters[position - 1].getThumbnail())
                }
                holder.itemView.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                    if (position == 0) image.setImageBitmap(originBitmap)
                    else image.setImageBitmap(ImageFilters.filters[position - 1].todo(originBitmap))
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        sourceBitmap?.run { ImageFilters.clear(this) }
        sourceBitmap = null
    }

}