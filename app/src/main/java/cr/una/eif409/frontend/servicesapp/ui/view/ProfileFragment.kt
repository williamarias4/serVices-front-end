package cr.una.eif409.frontend.servicesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.databinding.FragmentProfileBinding
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.profileViewModel = viewModel

        viewModel.getLoggedUser()
        listenForActions()

        return binding.root
    }

    private fun listenForActions() {
        viewModel.fullName.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                binding.profileFragmentEtName.error = "Nombre requerido"
                binding.profileFragmentEtName.requestFocus()
            }
        }

        viewModel.username.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                binding.profileFragmentEtUsername.error = "Usuario requerido"
                binding.profileFragmentEtUsername.requestFocus()
            }
        }

        viewModel.email.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                binding.profileFragmentEtEmail.error = "Email requerido"
                binding.profileFragmentEtEmail.requestFocus()
            }
        }

        viewModel.phoneNumber.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                binding.profileFragmentEtPhoneNumber.error = "Teléfono requerido"
                binding.profileFragmentEtPhoneNumber.requestFocus()
            }
        }

        viewModel.message.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                viewModel.message.postValue(null)
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}