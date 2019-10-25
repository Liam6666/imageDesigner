package yzx.app.image.design.utils

import com.blankj.utilcode.util.FileIOUtils
import java.io.File

object LocalData {

    private val dir = File(application.filesDir, "dLocalData")

    private fun targetFileName(key: String) = "${key}.ld"


    fun save(key: String, value: String) {
        dir.mkdir()
        FileIOUtils.writeFileFromString(File(dir, targetFileName(key)), value)
    }


    fun get(key: String): String? = FileIOUtils.readFile2String(File(dir, targetFileName(key)))


    fun delete(key: String) {
        File(dir, targetFileName(key)).delete()
    }


}