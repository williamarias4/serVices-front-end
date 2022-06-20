package cr.una.eif409.frontend.servicesapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cr.una.eif409.frontend.servicesapp.R
import cr.una.eif409.frontend.servicesapp.databinding.FragmentEditServiceBinding
import cr.una.eif409.frontend.servicesapp.ui.viewmodel.MyServicesViewModel

class EditServiceFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentEditServiceBinding
    private val viewModel: MyServicesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_service, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.myServicesViewModel = viewModel

        loadSpinnerData()
        listenForEvents()

        return binding.root
    }

    // Spinner on item selected listener
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        viewModel.selectedService.value?.category = parent.getItemAtPosition(pos).toString()
    }

    // Spinner on nothing selected listener
    override fun onNothingSelected(parent: AdapterView<*>) {
        viewModel.selectedService.value?.category = ""
    }

    private fun loadSpinnerData() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.services_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.editServiceFragmentSpType.adapter = adapter
            val pos = adapter.getPosition(viewModel.selectedService.value?.category)
            binding.editServiceFragmentSpType.setSelection(pos)
        }

        binding.editServiceFragmentSpType.onItemSelectedListener = this
    }

    private fun listenForEvents() {
        viewModel.message.observe(viewLifecycleOwner) {
            if (it != null) {
                showMessage(it)
                viewModel.message.value = null
            }
        }

        viewModel.deleted.observe(viewLifecycleOwner) {
            if (it != null && it) {
                requireActivity().onBackPressed()
                viewModel.deleted.value = null
            }
        }

        viewModel.updated.observe(viewLifecycleOwner) {
            if (it != null && it) {
                requireActivity().onBackPressed()
                viewModel.updated.value = null
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}