package yzx.app.image.design

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yzx.app.image.design.utils.toast
import yzx.app.image.media_offer.MediaOffer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        MediaOffer.requestImage(this, { path ->

            toast(path ?: "null")

        })

    }

}
