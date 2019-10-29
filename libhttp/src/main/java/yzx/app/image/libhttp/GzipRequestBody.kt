package yzx.app.image.libhttp

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.GzipSink
import okio.buffer


class GzipRequestBody(private val body: RequestBody) : RequestBody() {

    override fun contentLength(): Long = -1
    override fun contentType(): MediaType? = body.contentType()

    override fun writeTo(sink: BufferedSink) {
        val gzipSink = GzipSink(sink).buffer()
        body.writeTo(gzipSink)
        gzipSink.close()
    }

}