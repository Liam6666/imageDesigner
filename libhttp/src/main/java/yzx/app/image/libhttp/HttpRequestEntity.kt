package yzx.app.image.libhttp

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.GzipSink
import okio.buffer


class HttpRequestEntity(private val client: OkHttpClient) {


    private lateinit var url: String
    private var params: HttpStringMap? = null
    private var headers: HttpStringMap? = null
    private var content: String? = null
    private var gzip = false
    private var files: MutableList<UploadFileInfo>? = null
    private var tag: Any? = null
    private var cancelHandle: HttpCancelHandle? = null

    fun url(url: String) = apply { this.url = url }
    fun params(p: HttpStringMap) = apply { this.params = p }
    fun postContent(content: String, gzip: Boolean = false) = apply { this.content = content; this.gzip = gzip }
    fun header(headers: HttpStringMap) = apply { this.headers = headers }
    fun files(files: MutableList<UploadFileInfo>) = apply { this.files = files }
    fun tag(tag: Any) = apply { this.tag = tag }
    fun cancelHandle(handle: HttpCancelHandle) = apply { this.cancelHandle = handle }


    fun get() = HttpResponse().apply {
        doRequest(Request.Builder().run {
            headers?.entries?.forEach { addHeader(it.key, it.value) }
            tag(tag).url("${url}${buildGetParams()}").get().build()
        }, this)
    }


    fun post() = HttpResponse().apply {
        val body = MultipartBody.Builder().run {
            content?.runCatching { addPart(if (gzip) GzipRequestBody(toByteArray()) else toRequestBody()) }
            params?.entries?.forEach { addFormDataPart(it.key, it.value) }
            files?.forEach {
                it.file?.run { addFormDataPart(it.key!!, it.fileName!!, it.file!!.asRequestBody()) }
                it.bytes?.run { addFormDataPart(it.key!!, it.fileName!!, it.bytes!!.toRequestBody()) }
            }
            build()
        }
        val request = Request.Builder().run {
            headers?.entries?.forEach { addHeader(it.key, it.value) }
            if (gzip) addHeader("Content-Encoding", "gzip")
            tag(tag).url(url).post(body).build()
        }
        doRequest(request, this)
    }


    private fun doRequest(request: Request, result: HttpResponse) {
        val call = client.newCall(request)
        cancelHandle?.call = call
        call.execute().apply {
            result.code = code
            result.bodyString = body?.string()
        }
    }


    //
    //


    private class GzipRequestBody(val bytes: ByteArray) : RequestBody() {
        override fun contentType(): MediaType? = "charset=utf-8".toMediaTypeOrNull()
        override fun writeTo(sink: BufferedSink) {
            GzipSink(sink).buffer().runCatching { write(bytes); close() }
        }
    }


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