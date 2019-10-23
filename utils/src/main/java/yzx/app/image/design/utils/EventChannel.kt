package yzx.app.image.design.utils


interface EventCallback {
    fun <DataType> onEvent(what: String, data: DataType)
}


private val events = HashMap<String, ArrayList<EventCallback>>()


fun Any.listenEvent(what: String, callback: EventCallback) {
    runOnMainThread {
        var list = events[what]
        if (list == null)
            events[what] = ArrayList<EventCallback>().apply { list = this }
        list!!.add(callback)
    }
}


fun <DataType> Any.sendEvent(what: String, data: DataType? = null) {
    runOnMainThread {
        val cbs = events[what]
        cbs?.forEach { it.onEvent(what, data) }
    }
}


fun Any.cancelListenEvent(what: String) {
    runOnMainThread { events.remove(what) }
}


fun Any.cancelListenEvent(callback: EventCallback) {
    runOnMainThread { events.values.forEach { it.remove(callback) } }
}