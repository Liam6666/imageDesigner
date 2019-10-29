package yzx.app.image.libhttp

import okhttp3.OkHttpClient


class HttpRequestBase(internal val client: OkHttpClient, internal val url: String) {

    internal var headers: HttpStringMap? = null
    internal var gzip = false
    internal var tag: Any? = null
    internal var cancelHandle: HttpCancelHandle? = null


    /** 添加header */
    fun header(headers: HttpStringMap) = apply { this.headers = headers }

    /** 设置tag, 用来cancel请求 */
    fun tag(tag: Any) = apply { this.tag = tag }

    /** 设置cancel, 用来cancel请求 */
    fun cancelHandle(handle: HttpCancelHandle) = apply { this.cancelHandle = handle }

    /** gzip */
    fun gzip(gzip: Boolean) = apply { this.gzip = gzip }

    /** 表单post */
    fun formPost() = HttpFormPost(this)

    /** 文件提交 */
    fun multipartPost() = HttpMultipartPost(this)

    /** json提交 */
    fun contentPost() = HttpContentPost(this)

    /** get */
    fun get() = HttpGet(this)

}