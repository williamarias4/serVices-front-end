package cr.una.eif409.frontend.servicesapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.core.SharedApp

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // If user is logged in, go to MainActivity
        if (SharedApp.preferences.username.isNotBlank()) {
            navigateToMainActivity()
        }

        // Setup the navigation controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup the top app bar
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.auth_toolbar)
            .setupWithNavController(navController, appBarConfiguration)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
