package yzx.app.image.libhttp

import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


abstract class HttpRequestAble internal constructor(private val baseInfo: HttpRequestBase) {

    companion object {
        const val METHOD_GET = "GET"
        const val METHOD_POST = "POST"
    }

    @Volatile
    private var hasRequested = false


    /* 防止一个实例重复请求 */
    private fun checkRequested() {
        if (hasRequested)
            throw IllegalStateException("entity has requested")
        hasRequested = true
    }

    /** url后缀补充 */
    open fun getUrlSuffix(): String = ""

    /** post的请求body */
    abstract fun getRequestBody(): RequestBody?

    /** 请求方法  GET/POST */
    abstract fun getMethod(): String


    /** 发起请求 */
    fun doRequest() = HttpResponse().apply {
        checkRequested()
        val request = Request.Builder().run {
            url("${baseInfo.url}${getUrlSuffix()}")
            tag(baseInfo.tag)
            baseInfo.headers?.entries?.forEach { addHeader(it.key, it.value) }
            if (baseInfo.gzip) addHeader("Content-Encoding", "gzip")
            var originBody = getRequestBody()
            if (METHOD_POST == getMethod() && originBody == null)
                originBody = "".toRequestBody()
            method(getMethod(), if (baseInfo.gzip && originBody != null) GzipRequestBody(originBody) else originBody)
            build()
        }
        val call = baseInfo.client.newCall(request)
        baseInfo.cancelHandle?.call = call
        val result = this
        runCatching {
            call.execute().apply {
                result.code = code
                result.bodyString = body?.string()
            }
        }.onFailure { err ->
            when {
                err.toString().contains("Socket closed") -> {
                    result.code = HttpResponse.code_task_cancel
                    result.bodyString = null
                }
                else -> {
                    result.code = HttpResponse.code_network_error
                    result.bodyString = null
                }
            }
        }
    }

}