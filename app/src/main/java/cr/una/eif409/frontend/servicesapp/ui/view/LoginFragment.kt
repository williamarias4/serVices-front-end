package cr.una.eif409.frontend.servicesapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.databinding.FragmentLoginBinding
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel

        login()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    private fun login() {
        // Observe for changes in the input fields
        loginViewModel.userLogin.observe(requireActivity()) {
            if (it.username.isNullOrBlank()) {
                binding.loginActivityEtEmail.error = "Correo electrónico requerido"
                binding.loginActivityEtEmail.requestFocus()
            } else if (it.password.isNullOrEmpty()) {
                binding.loginActivityEtPassword.error = "Contraseña requerida"
                binding.loginActivityEtPassword.requestFocus()
            }
        }

        // Observe for changes in the login status
        loginViewModel.isValidUser.observe(requireActivity()) {
            when (it) {
                true -> navigateToMainActivity()
                false -> showMessage("Usuario o contraseña incorrectos")
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}