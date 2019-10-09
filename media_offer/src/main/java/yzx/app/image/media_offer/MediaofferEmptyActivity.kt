package yzx.app.image.media_offer

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yzx.app.image.design.utils.launchActivity


class MediaOfferEmptyActivity : AppCompatActivity() {


    companion object {
        fun launch(context: Context, sign: String, type: String) {
            context.launchActivity<MediaOfferEmptyActivity>(
                "s" to sign, "t" to type
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private fun doOver(path: String) {

        finish()
    }

}