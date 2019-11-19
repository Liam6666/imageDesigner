package yzx.app.image.design

import androidx.multidex.MultiDexApplication
import yzx.app.image.design.other.AndroidPNotice
import yzx.app.image.design.utils.AppPageRecord
import yzx.app.image.design.utils.application
import yzx.app.image.libhttp.HttpClient
import yzx.app.image.libhttp.HttpClientInitConfig


class App : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        application = this
        AndroidPNotice.closeAndroidPDialog()
        AppPageRecord.init(this)
        HttpClient.initClient(HttpClientInitConfig().apply {})
    }

}