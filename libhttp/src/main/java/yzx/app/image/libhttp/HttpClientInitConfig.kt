package yzx.app.image.libhttp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager


class HttpClientInitConfig {

    var connTimeout = 6000L
    var readTimeout = 6000L
    var writeTimeout = 6000L
    val interceptors = LinkedList<Interceptor>()
    var sslSocketFactory: SSLSocketFactory? = null
    var trustManager: X509TrustManager? = null
    var hostnameVerifier: HostnameVerifier? = null

    fun allTimeout(time: Long) = apply { connTimeout = time; readTimeout = time; writeTimeout = time }

    /**
     * build OKHttpClient by config
     */
    internal fun buildClient(): OkHttpClient = OkHttpClient.Builder().run {
        connectTimeout(connTimeout, TimeUnit.MILLISECONDS)
        readTimeout(readTimeout, TimeUnit.MILLISECONDS)
        writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
        interceptors.forEach { addInterceptor(it) }
        sslSocketFactory?.run { trustManager?.run { sslSocketFactory(sslSocketFactory!!, trustManager!!) } }
        hostnameVerifier?.run { hostnameVerifier(this) }
        build()
    }

}