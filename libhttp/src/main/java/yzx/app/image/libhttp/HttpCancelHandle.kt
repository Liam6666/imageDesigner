package yzx.app.image.libhttp

import okhttp3.Call


class HttpCancelHandle {

    @Volatile
    internal var call: Call? = null

    /**
     * cancel task
     */
    fun cancel() = call?.cancel()

}