package eu.kevin.composedemo

import android.app.Application
import demo.helpers.initKoin
import org.koin.android.ext.koin.androidContext

class ComposeDemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(applicationContext)
        }
    }
}