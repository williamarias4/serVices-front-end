package cr.una.eif409.frontend.servicesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.databinding.FragmentNewServiceBinding
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.NewServiceViewModel

class NewServiceFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentNewServiceBinding
    private lateinit var newServiceViewModel: NewServiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        newServiceViewModel = ViewModelProvider(this).get(NewServiceViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_service, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.newServiceViewModel = newServiceViewModel

        loadSpinnerData()
        listenForActions()

        return binding.root
    }

    // Spinner on item selected listener
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        newServiceViewModel.type.value = parent.getItemAtPosition(pos).toString()
    }

    // Spinner on nothing selected listener
    override fun onNothingSelected(parent: AdapterView<*>) {
        newServiceViewModel.type.value = ""
    }

    private fun loadSpinnerData() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.services_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.newServiceFragmentSpType.adapter = adapter
        }

        binding.newServiceFragmentSpType.onItemSelectedListener = this
    }

    private fun listenForActions() {
        // Observe for changes in the input fields
        newServiceViewModel.serviceInput.observe(viewLifecycleOwner) {
            when {
                it.title.isNullOrBlank() -> {
                    binding.newServiceFragmentEtTitle.error = "Titulo requerido"
                    binding.newServiceFragmentEtTitle.requestFocus()
                }
                it.type.isNullOrBlank() -> {
                    val textView = binding.newServiceFragmentSpType.selectedView as TextView
                    textView.error = ""
                }
                it.price.isNullOrBlank() -> {
                    binding.newServiceFragmentEtPrice.error = "Precio requerido"
                    binding.newServiceFragmentEtPrice.requestFocus()
                }
                it.description.isNullOrBlank() -> {
                    binding.newServiceFragmentEtDescription.error = "Descripcion requerida"
                    binding.newServiceFragmentEtDescription.requestFocus()
                }
            }
        }

        // Observe for changes in the register process
        newServiceViewModel.registered.observe(viewLifecycleOwner) {
            if (it) {
                showMessage("Servicio publicado correctamente")
                val action = NewServiceFragmentDirections.actionNewServiceFragmentToHomeFragment()
                view?.findNavController()?.navigate(action)
            }
        }

        // Observe for error messages
        newServiceViewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                newServiceViewModel.errorMessage.postValue(null)
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}