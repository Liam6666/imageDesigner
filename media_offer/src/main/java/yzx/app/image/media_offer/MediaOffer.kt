package yzx.app.image.media_offer

import android.content.Context


typealias PathCallback = (path: String?) -> Unit


object MediaOffer {

    val type_image = "image"

    internal val callbacks = HashMap<String, PathCallback>()


    fun requestImage(context: Context, callback: PathCallback) {
        val sign = System.currentTimeMillis().toString()
        val type = type_image
        callbacks.put(sign, callback)
        MediaOfferEmptyActivity.launch(context, sign, type)
    }


}