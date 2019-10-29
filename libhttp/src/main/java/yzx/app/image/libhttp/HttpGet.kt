package yzx.app.image.libhttp

import okhttp3.RequestBody


class HttpGet(baseInfo: HttpRequestBase) : HttpRequestAble(baseInfo) {

    override fun getRequestBody(): RequestBody? = null
    override fun getUrlSuffix(): String = Util.buildGetParams(params)
    override fun getMethod(): String = METHOD_GET
    private var params: HttpStringMap? = null
    fun params(params: HttpStringMap) = apply { this.params = params }

}