package yzx.app.image.libhttp

import okhttp3.OkHttpClient

object HttpClient {


    private lateinit var client: OkHttpClient


    fun initClient(config: HttpClientInitConfig) = run { client = config.buildClient(); Unit }


    fun url(url: String): HttpRequestEntity = HttpRequestEntity(client).url(url)


    fun cancel(tag: Any) = run {
        client.dispatcher.queuedCalls().forEach { if (it.request().tag() == tag) it.cancel() }
        client.dispatcher.runningCalls().forEach { if (it.request().tag() == tag) it.cancel() }
    }

}