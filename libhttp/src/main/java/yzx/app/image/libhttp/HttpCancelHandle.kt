package yzx.app.image.libhttp

import okhttp3.Call


class HttpCancelHandle {

    @Volatile
    internal var call: Call? = null

    fun cancel() = call?.cancel()

}