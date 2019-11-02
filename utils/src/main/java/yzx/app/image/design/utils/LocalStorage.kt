package yzx.app.image.design.utils

import java.io.File
import java.io.FileReader
import java.io.FileWriter


interface LocalStorage {
    fun save(key: String, data: String?): Boolean
    fun get(key: String): String?
    fun delete(key: String)
}


val localStorage = localStorage(File(application.filesDir, "__localStorage__"))


private fun localStorage(dir: File): LocalStorage {
    if (!dir.exists())
        dir.mkdirs()
    return object : LocalStorage {
        /* save */
        override fun save(key: String, data: String?): Boolean {
            if (data == null) {
                delete(key)
                return true
            }
            var result = true
            runCatching {
                FileWriter(File(dir, key)).apply {
                    write(data); flush(); close()
                }
            }.onFailure { result = false }
            return result
        }

        /* get */
        override fun get(key: String): String? {
            FileReader(File(dir, key).apply {
                if (!exists()) return null
            }).apply {
                var result: String? = null
                runCatching { result = readText(); close() }
                return result
            }
        }

        /* delete */
        override fun delete(key: String) {
            File(dir, key).delete()
        }
    }
}