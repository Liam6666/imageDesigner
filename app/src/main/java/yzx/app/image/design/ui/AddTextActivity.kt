package yzx.app.image.design.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.activity_add_text.*
import yzx.app.image.design.R
import yzx.app.image.design.logicViews.AddTextPanel
import yzx.app.image.design.utils.*


class AddTextActivity : AppCompatActivity(), IImageDesignActivity {

    companion object {
        fun launch(activity: Context, path: String) = activity.launchActivity<AddTextActivity>("f" to path)
    }


    private val filePath by lazy { intent?.getStringExtra("f") ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (filePath.isEmpty()) {
            finish()
            return
        }
        makeStatusBar()
        setContentView(R.layout.activity_add_text)
        decodeFileBitmapWithMaxLength(this, filePath, BitmapDecodeOptions.decodeBitmapMaxLength) { originBitmap ->
            if (originBitmap == null) {
                onBitmapLoadedError()
            } else {
                setUI(originBitmap)
            }
        }
    }


    private fun setUI(originBitmap: Bitmap) {
        image.setImageBitmap(originBitmap)
        image.getRealImageWidthAndHeight { result ->
            textBox.layoutParams.width = result.x
            textBox.layoutParams.height = result.y
            textBox.requestLayout()
        }
        add.setOnClickListener { showAddTextInput() }
        save.setOnClickListener { }
        cache.setOnClickListener { }
        textBox.onTextViewClick = { innerTextView -> showEditPanel(innerTextView) }
    }


    /* 弹出添加输入文字的弹框 */
    private fun showAddTextInput() {
        val view = inflateView(this, R.layout.layout_dialog_add_text, (window.decorView as ViewGroup))
        showDialog(view, resources.displayMetrics.widthPixels / 11 * 9, -2) { dialog ->
            dialog.setCancelable(false)
            val input = view.findViewById<EditText>(R.id.input)
            view.findViewById<View>(R.id.confirm).setOnClickListener {
                val inputTxt = input.text.toString().trim()
                if (inputTxt.isEmpty()) {
                    toast("文字不能为空")
                } else {
                    textBox.add(text = inputTxt)
                    dismissDialog(dialog)
                }
            }
            view.findViewById<View>(R.id.cancel).setOnClickListener {
                dismissDialog(dialog)
            }
            view.post { KeyboardUtils.showSoftInput(view.findViewById<EditText>(R.id.input)) }
        }
    }


    private val panelID = View.generateViewId()

    /* 显示调整文字样式的面板 */
    private fun showEditPanel(tv: TextView) {
        val panel = AddTextPanel(this).apply {
            setTextView(tv)
            id = panelID
            layoutParams = ViewGroup.LayoutParams(-1, -1)
            getCloseView().setOnClickListener { dismissPanel() }
            translationX = resources.displayMetrics.widthPixels.toFloat()
        }
        (window.decorView as ViewGroup).addView(panel)
        panel.post {
            if (panel.tag == "1") return@post
            panel.animate().translationX(0f).setDuration(150).start()
        }
    }

    /* 关闭调整文字样式的面板 */
    private fun dismissPanel(): Boolean {
        val panel = findViewById<View>(panelID) ?: return false
        panel.setOnTouchListener { _, _ -> true }
        panel.tag = "1"
        panel.animate().cancel()
        panel.animate().translationX(resources.displayMetrics.widthPixels.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    (panel.parent as ViewGroup).removeView(panel)
                }
            })
            .setDuration(150).start()
        return true
    }


    override fun onBackPressed() {
        if (!dismissPanel())
            super.onBackPressed()
    }

}