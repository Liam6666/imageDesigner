package yzx.app.image.design

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils
import kotlinx.android.synthetic.main.activity_main.*
import yzx.app.image.design.logicViews.MainListHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BarUtils.setStatusBarLightMode(this, true)
        MainListHelper.makeItem(this, recyclerView)
    }

}
