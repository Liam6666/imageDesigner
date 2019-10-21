package yzx.app.image.design.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*
import kotlin.collections.ArrayList


object AppPageRecord {

    const val created = 1
    const val started = 2
    const val resumed = 3
    const val paused = 4
    const val stopped = 5


    private val data = WeakHashMap<Activity, Int>()


    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}
            override fun onActivityPaused(activity: Activity) {
                data.put(activity, paused)
            }

            override fun onActivityResumed(activity: Activity) {
                data.put(activity, resumed)
            }

            override fun onActivityStarted(activity: Activity) {
                data.put(activity, started)
            }

            override fun onActivityDestroyed(activity: Activity) {
                data.remove(activity)
            }

            override fun onActivityStopped(activity: Activity) {
                data.put(activity, stopped)
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                data.put(activity, created)
            }
        })
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    //
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /** app是否处在前台 */
    fun isAppForeground(): Boolean {
        data.values.forEach {
            if (it == resumed || it == started)
                return true
        }
        return false
    }


    /** app是否处在后台 */
    fun isAppBackground(): Boolean {
        return !isAppForeground()
    }


    /** 获取栈顶activity */
    fun getResumedActivity(): Activity? {
        data.keys.forEach {
            val state = data[it]
            if (state == resumed || state == started)
                return it
        }
        return null
    }

    /** 界面是否存在 */
    fun isActivityExist(clz: Class<Activity>): Boolean {
        data.keys.forEach {
            if (!it.isFinishing && it.javaClass.canonicalName == clz.canonicalName)
                return true
        }
        return false
    }


    /** 获取存在的activity的实例 */
    fun <T> getExistsActivities(clz: Class<T>): MutableList<T> {
        val list = ArrayList<T>(2)
        data.keys.forEach {
            if (!it.isFinishing && it.javaClass.canonicalName == clz.canonicalName)
                list.add(it as T)
        }
        return list
    }


}