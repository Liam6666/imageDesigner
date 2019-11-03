package yzx.app.image.design.utils

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList


typealias EventListener = (what: String, data: Any?) -> Unit

enum class AppEventThreadType {
    MAIN,
    BACKGROUND,
    CURRENT
}

interface AppEvent {
    fun registerAppEvent(what: String, listener: EventListener)
    fun unregisterAppEvent(what: String)
    fun unregisterAppEvent(listener: EventListener)
    fun postAppEvent(what: String, data: Any? = null, callbackMode: AppEventThreadType = AppEventThreadType.CURRENT)
}


//


val appEvent = appEvent()


private fun appEvent(): AppEvent {
    return object : AppEvent {
        val handler = Handler(Looper.getMainLooper())
        val events = ConcurrentHashMap<String, MutableList<EventListener>>()
        val backgroundScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
        val lock = Any()
        override fun registerAppEvent(what: String, listener: EventListener) {
            synchronized(lock) {
                val list = events[what] ?: Collections.synchronizedList(ArrayList<EventListener>()).apply { events[what] = this }
                if (!list.contains(listener)) list.add(listener)
            }
        }

        override fun unregisterAppEvent(what: String) {
            synchronized(lock) { events.remove(what) }
        }

        override fun unregisterAppEvent(listener: EventListener) {
            synchronized(lock) { events.entries.forEach { it.value.remove(listener) } }
        }

        override fun postAppEvent(what: String, data: Any?, callbackMode: AppEventThreadType) {
            var forList: ArrayList<EventListener>
            synchronized(lock) {
                val originList = events[what]
                if (originList.isNullOrEmpty())
                    return
                forList = ArrayList(originList)
            }
            forList.forEach { callback ->
                when (callbackMode) {
                    AppEventThreadType.CURRENT -> {
                        callback.invoke(what, data)
                    }
                    AppEventThreadType.MAIN -> {
                        handler.post {
                            callback.invoke(what, data)
                        }
                    }
                    AppEventThreadType.BACKGROUND -> {
                        backgroundScope.launch {
                            callback.invoke(what, data)
                        }
                    }
                }
            }
        }
    }
}
