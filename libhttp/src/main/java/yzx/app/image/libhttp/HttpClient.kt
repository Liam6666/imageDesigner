package yzx.app.image.libhttp

import okhttp3.OkHttpClient

object HttpClient {


    private lateinit var client: OkHttpClient


    /**
     * 初始化配置
     */
    fun initClient(config: HttpClientInitConfig) = run { client = config.buildClient(); Unit }


    /**
     *
     */
    fun url(url: String): HttpRequestBase = HttpRequestBase(client, url)


    /**
     * 根据tag取消请求
     */
    fun cancel(tag: Any) = run {
        client.dispatcher.queuedCalls().forEach { if (it.request().tag() == tag) it.cancel() }
        client.dispatcher.runningCalls().forEach { if (it.request().tag() == tag) it.cancel() }
    }

}