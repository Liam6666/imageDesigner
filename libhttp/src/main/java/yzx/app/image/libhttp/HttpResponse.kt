package yzx.app.image.libhttp

class HttpResponse {

    companion object {
        val code_network_error = -1
        val code_task_cancel = -2
    }

    var code = 0
    var bodyString: String? = null

}