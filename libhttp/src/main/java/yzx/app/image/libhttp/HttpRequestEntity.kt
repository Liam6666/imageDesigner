package yzx.app.image.libhttp

import okhttp3.OkHttpClient
import okhttp3.Request


class HttpRequestEntity(private val client: OkHttpClient) {

    private lateinit var url: String
    private var params: HttpStringMap? = null
    private var headers: HttpStringMap? = null
    private var content: String? = null
    private var gzip = false
    private var files: MutableList<UploadFileInfo>? = null
    private var tag: Any? = null

    fun url(url: String) = apply { this.url = url }
    fun params(p: HttpStringMap) = apply { this.params = p }
    fun postContent(content: String, gzip: Boolean = false) = apply { this.content = content; this.gzip = gzip }
    fun header(headers: HttpStringMap) = apply { this.headers = headers }
    fun files(files: MutableList<UploadFileInfo>) = apply { this.files = files }
    fun tag(tag: Any) = apply { this.tag = tag }


    fun get() = HttpResponse().apply {
        val realUrl = "${url}${buildGetParams()}"
        val resp = client.newCall(Request.Builder().run { tag(tag).url(realUrl).build() }).execute()
        code = resp.code
        bodyString = resp.body?.string()
    }


    fun post() {

    }


    //
    //


    private fun buildGetParams(): String {
        if (params.isNullOrEmpty())
            return ""
        val sb = StringBuilder("?")
        for (key in params!!.keys) {
            val value = params!![key]
            if (value != null && key.isNotEmpty() && value.isNotEmpty())
                sb.append(key).append("=").append(value).append("&")
        }
        val lastIndex = sb.length - 1
        if (sb[lastIndex] == '&')
            sb.deleteCharAt(lastIndex)
        return sb.toString()
    }

}