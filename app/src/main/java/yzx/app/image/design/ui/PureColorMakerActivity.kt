package yzx.app.image.design.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pure_color_maker.*
import yzx.app.image.design.R
import yzx.app.image.design.utils.launchActivity


class PureColorMakerActivity : AppCompatActivity(), IImageDesignActivity {


    companion object {
        fun launch(activity: Context) = activity.launchActivity<PureColorMakerActivity>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBar()
        setContentView(R.layout.activity_pure_color_maker)
        makeUI()
    }


    private fun makeUI() {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = notifyExampleView()
        }.apply {
            widthInput.addTextChangedListener(this)
            heightInput.addTextChangedListener(this)
        }
        colorPicker.onColorChanged = { notifyExampleView() }
        cache.setOnClickListener { }
        save.setOnClickListener { }
    }


    private fun notifyExampleView() {
        val widthInputStr = widthInput.text.toString()
        val heightInputStr = heightInput.text.toString()
        if (widthInputStr.isEmpty() || heightInputStr.isEmpty()) {
            viewer.visibility = View.INVISIBLE
        } else {
            val color = colorPicker.getCurrentColor()
            val w = widthInputStr.toInt()
            val h = heightInputStr.toInt()
            viewer.visibility = View.VISIBLE
            viewer.setBackgroundColor(color)
            when {
                w > h -> {
                    viewer.layoutParams.width = (viewer.parent as View).width
                    viewer.layoutParams.height = viewer.layoutParams.width * h / w
                    viewer.requestLayout()
                }
                w < h -> {
                    viewer.layoutParams.height = (viewer.parent as View).height
                    viewer.layoutParams.width = viewer.layoutParams.height * w / h
                    viewer.requestLayout()
                }
                w == h -> {
                    viewer.layoutParams.width = -1
                    viewer.layoutParams.height = -1
                    viewer.requestLayout()
                }
            }
        }
    }

}