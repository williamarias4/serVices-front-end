package cr.una.eif409.frontend.servicesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.databinding.FragmentLoginBinding
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginViewModel = loginViewModel

        listenForActions()

        return binding.root
    }

    private fun listenForActions() {
        // Observe for changes in the input fields
        loginViewModel.userLogin.observe(viewLifecycleOwner) {
            when {
                it.username.isNullOrBlank() -> {
                    binding.loginActivityEtUsername.error = "Usuario requerido"
                    binding.loginActivityEtUsername.requestFocus()
                }
                it.password.isNullOrBlank() -> {
                    binding.loginActivityEtPassword.error = "Contraseña requerida"
                    binding.loginActivityEtPassword.requestFocus()
                }
            }
        }

        // Observe for changes in the login status
        loginViewModel.isValidUser.observe(viewLifecycleOwner) {
            if (it) navigateToMainActivity()
        }

        // Observe for error messages
        loginViewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                loginViewModel.errorMessage.postValue(null)
            }
        }

        // Navigate to register fragment when the register button is clicked
        binding.loginActivityTvSignup.setOnClickListener {
            navigateToRegisterFragment()
        }
    }

    private fun navigateToRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        val navController = requireParentFragment().findNavController()
        navController.navigate(action)
    }

    private fun navigateToMainActivity() {
        (activity as AuthActivity).navigateToMainActivity()
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
