package yzx.app.image.libhttp

import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class HttpContentPost(baseInfo: HttpRequestBase) : HttpRequestAble(baseInfo) {

    private var data1: ByteArray? = null
    private var data2: String? = null
    private var data3: File? = null


    fun body(data: ByteArray) = apply {
        this.data1 = data
        this.data2 = null
        this.data3 = null
    }

    fun body(data: String) = apply {
        this.data1 = null
        this.data2 = data
        this.data3 = null
    }

    fun body(data: File) = apply {
        this.data1 = null
        this.data2 = null
        this.data3 = data
    }


    override fun getRequestBody(): RequestBody? {
        var body = data1?.toRequestBody()
        if (body == null) body = data2?.toRequestBody()
        if (body == null) body = data3?.asRequestBody()
        return body
    }


    override fun getMethod(): String = METHOD_POST


}