package yzx.app.image.libhttp

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class HttpMultipartPost(baseInfo: HttpRequestBase) : HttpRequestAble(baseInfo) {

    private var files: MutableList<UploadFileInfo>? = null
    private var params: HttpStringMap? = null


    fun params(params: HttpStringMap) = apply { this.params = params }
    fun files(files: MutableList<UploadFileInfo>) = apply { this.files = files }


    override fun getRequestBody(): RequestBody? {
        if (files.isNullOrEmpty() && params.isNullOrEmpty())
            return null
        return MultipartBody.Builder().run {
            files?.forEach { addFormDataPart(it.key!!, it.fileName, it.bytes?.toRequestBody() ?: it.file!!.asRequestBody()) }
            params?.entries?.forEach { addFormDataPart(it.key, it.value) }
            build()
        }
    }

    override fun getMethod(): String = METHOD_POST


}