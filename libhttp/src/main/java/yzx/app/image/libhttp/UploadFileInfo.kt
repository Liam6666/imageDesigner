package yzx.app.image.libhttp

import java.io.File

class UploadFileInfo {

    var key: String? = null

    var fileName: String? = null

    var file: File? = null
        set(value) {
            field = value
            bytes?.run { throw IllegalStateException("bytes is not null") }
        }
    var bytes: ByteArray? = null
        set(value) {
            field = value
            file?.run { throw IllegalStateException("file is not null") }
        }

}