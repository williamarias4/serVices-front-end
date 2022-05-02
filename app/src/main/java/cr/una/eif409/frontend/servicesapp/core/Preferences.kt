package cr.una.eif409.frontend.servicesapp.core

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val preferencesName = "cr.una.eif409.frontend.servicesapp"
    private val preferences: SharedPreferences = context.getSharedPreferences(preferencesName, 0)
    private val storedUsername = ""

    var username: String
        get() = preferences.getString(storedUsername, "")!!
        set(value) = preferences.edit().putString(storedUsername, value).apply()
}
