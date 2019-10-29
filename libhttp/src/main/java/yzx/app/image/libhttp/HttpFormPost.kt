package yzx.app.image.libhttp

import okhttp3.FormBody
import okhttp3.RequestBody


class HttpFormPost(baseInfo: HttpRequestBase) : HttpRequestAble(baseInfo) {

    private var params: HttpStringMap? = null
    fun params(params: HttpStringMap) = apply { this.params = params }



    override fun getRequestBody(): RequestBody? {
        if (!params.isNullOrEmpty()) {
            return FormBody.Builder().run {
                params!!.entries.forEach { add(it.key, it.value) }; build()
            }
        } else {
            return null
        }
    }

    override fun getMethod(): String = METHOD_POST


}