package cr.una.eif409.frontend.servicesapp.core

import android.app.Application

class SharedApp : Application() {
    companion object {
        lateinit var preferences: Preferences
    }

    override fun onCreate() {
        super.onCreate()
        preferences = Preferences(applicationContext)
    }
}
