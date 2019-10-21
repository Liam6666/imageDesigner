package yzx.app.image.design

import android.app.Application
import yzx.app.image.design.other.AndroidPNotice
import yzx.app.image.design.utils.AppPageRecord
import yzx.app.image.design.utils.application


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
        AndroidPNotice.closeAndroidPDialog()
        AppPageRecord.init(this)
    }

}