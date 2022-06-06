package cr.una.eif409.frontend.servicesapp.core

import android.content.Context

class Preferences(context: Context) {
    private val preferencesName = "cr.una.eif409.frontend.servicesapp"
    private val preferences = context.getSharedPreferences(preferencesName, 0)
    private val storedToken = ""

    var token: String
        get() = preferences.getString(storedToken, "")!!
        set(value) = preferences.edit().putString(storedToken, value).apply()
}
