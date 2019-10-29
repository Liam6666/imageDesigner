package yzx.app.image.libhttp

class HttpResponse {

    companion object {
        // code: 网络错误
        val code_network_error = -1
        // code: 请求被cancel掉
        val code_task_cancel = -2
    }


    // 服务器响应code
    var code = 0
    // 返回的消息体string
    var bodyString: String? = null

}