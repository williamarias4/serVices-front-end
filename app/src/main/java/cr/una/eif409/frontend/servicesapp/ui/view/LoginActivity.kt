package cr.una.eif409.frontend.servicesapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.core.SharedApp
import cr.una.eif409.frontend.servicesapp.databinding.ActivityLoginBinding
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        if (SharedApp.preferences.username.isNotBlank()) {
            navigateToMainActivity()
        }

        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel

        login()
    }

    private fun login() {
        // Observe for changes in the input fields
        loginViewModel.userLogin.observe(this) {
            if (it.username.isNullOrBlank()) {
                binding.loginActivityEtEmail.error = "Correo electrónico requerido"
                binding.loginActivityEtEmail.requestFocus()
            } else if (it.password.isNullOrEmpty()) {
                binding.loginActivityEtPassword.error = "Contraseña requerida"
                binding.loginActivityEtPassword.requestFocus()
            }
        }

        // Observe for changes in the login status
        loginViewModel.isValidUser.observe(this) {
            when (it) {
                true -> navigateToMainActivity()
                false -> showMessage("Usuario o contraseña incorrectos")
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
