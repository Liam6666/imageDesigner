package yzx.app.image.libhttp


internal object Util {


    /** 创建get请求参数 */
    fun buildGetParams(params: HttpStringMap?): String {
        if (params.isNullOrEmpty())
            return ""
        val sb = StringBuilder("?")
        for (key in params.keys) {
            val value = params[key]
            if (value != null && key.isNotEmpty() && value.isNotEmpty())
                sb.append(key).append("=").append(value).append("&")
        }
        val lastIndex = sb.length - 1
        if (sb[lastIndex] == '&')
            sb.deleteCharAt(lastIndex)
        return sb.toString()
    }

}