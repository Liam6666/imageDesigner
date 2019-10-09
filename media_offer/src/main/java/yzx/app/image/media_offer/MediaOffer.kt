package yzx.app.image.media_offer


typealias PathCallback = (path: String) -> Unit


object MediaOffer {

    val type_image = 1

    internal val callbacks = HashMap<String, PathCallback>()


    fun requestImage(callback: PathCallback) {
        val sign = System.currentTimeMillis().toString()
        val type = type_image


    }

}