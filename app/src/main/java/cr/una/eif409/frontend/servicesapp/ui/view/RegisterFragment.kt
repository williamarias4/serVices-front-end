package cr.una.eif409.frontend.servicesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.databinding.FragmentRegisterBinding
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.registerViewModel = registerViewModel

        listenForActions()

        return binding.root
    }

    private fun listenForActions() {
        // Observe for changes in the input fields
        registerViewModel.userSignup.observe(viewLifecycleOwner) {
            when {
                it.person?.fullName.isNullOrBlank() -> {
                    binding.registerFragmentEtFullName.error = "Nombre requerido"
                    binding.registerFragmentEtFullName.requestFocus()
                }
                it.username.isNullOrBlank() -> {
                    binding.registerFragmentEtUsername.error = "Usuario requerido"
                    binding.registerFragmentEtUsername.requestFocus()
                }
                it.person?.email.isNullOrBlank() -> {
                    binding.registerFragmentEtEmail.error = "Email requerido"
                    binding.registerFragmentEtEmail.requestFocus()
                }
                it.person?.phoneNumber.isNullOrBlank() -> {
                    binding.registerFragmentEtPhone.error = "Teléfono requerido"
                    binding.registerFragmentEtPhone.requestFocus()
                }
                it.password.isNullOrBlank() -> {
                    binding.registerFragmentEtPassword.error = "Contraseña requerida"
                    binding.registerFragmentEtPassword.requestFocus()
                }
                it.password != it.passwordConfirmation -> {
                    binding.registerFragmentEtPasswordConfirm.error = "Las contraseñas no coinciden"
                    binding.registerFragmentEtPasswordConfirm.requestFocus()
                }
            }
        }

        // Observe for changes in the register process
        registerViewModel.registered.observe(viewLifecycleOwner) {
            if (it) {
                showMessage("Usuario registrado correctamente")
                activity?.onBackPressed()
            }
        }

        // Observe for error messages
        registerViewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                registerViewModel.errorMessage.postValue(null)
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}